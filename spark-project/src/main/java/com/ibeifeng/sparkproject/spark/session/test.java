package com.ibeifeng.sparkproject.spark.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.storage.StorageLevel;

import com.alibaba.fastjson.JSONObject;
import com.ibeifeng.sparkproject.conf.ConfigurationManager;
import com.ibeifeng.sparkproject.constant.Constants;
import com.ibeifeng.sparkproject.dao.ITaskDAO;
import com.ibeifeng.sparkproject.dao.factory.DAOFactory;
import com.ibeifeng.sparkproject.domain.Task;
import com.ibeifeng.sparkproject.util.DateUtils;
import com.ibeifeng.sparkproject.util.ParamUtils;
import com.ibeifeng.sparkproject.util.SparkUtils;
import com.ibeifeng.sparkproject.util.StringUtils;

import it.unimi.dsi.fastutil.ints.IntList;
import scala.Tuple2;

public class test {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf()
						.setAppName(Constants.SPARK_APP_NAME_SESSION)
						.set("spark.storage.memoryFraction", "0.5")  
						.set("spark.shuffle.consolidateFiles", "true")
						.set("spark.shuffle.file.buffer", "64")  
						.set("spark.shuffle.memoryFraction", "0.3")    
						.set("spark.reducer.maxSizeInFlight", "24")  
						.set("spark.shuffle.io.maxRetries", "60")  
						.set("spark.shuffle.io.retryWait", "60")   
						.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
						.registerKryoClasses(new Class[]{
								CategorySortKey.class,
								IntList.class});   
		SparkUtils.setMaster(conf); 
		
		JavaSparkContext sc = new JavaSparkContext(conf);
//				sc.checkpointFile("hdfs://");
		SQLContext sqlContext = getSQLContext(sc.sc());
		
		// 生成模拟测试数据
		SparkUtils.mockData(sc, sqlContext);  
		
		// 创建需要使用的DAO组件
		ITaskDAO taskDAO = DAOFactory.getTaskDAO();
		
		// 首先得查询出来指定的任务，并获取任务的查询参数
		long taskid = ParamUtils.getTaskIdFromArgs(args, Constants.SPARK_LOCAL_TASKID_SESSION);
		Task task = taskDAO.findById(taskid);
		if(task == null) {
			System.out.println(new Date() + ": cannot find this task with id [" + taskid + "].");  
			return;
		}
		
		JSONObject taskParam = JSONObject.parseObject(task.getTaskParam());
		JavaRDD<Row> actionRDD = SparkUtils.getActionRDDByDateRange(sqlContext, taskParam);
		JavaPairRDD<String, Row> sessionid2actionRDD = getSessionid2ActionRDD(actionRDD);
		sessionid2actionRDD = sessionid2actionRDD.persist(StorageLevel.MEMORY_ONLY());
		JavaPairRDD<String, String> sessionid2AggrInfoRDD = 
						aggregateBySession(sc, sqlContext, sessionid2actionRDD);
		
	}
	
	
	
	private static SQLContext getSQLContext(SparkContext sc) {
		boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);
		if(local) {
			return new SQLContext(sc);
		} else {
			return new HiveContext(sc);
		}
	}
	
	public static JavaPairRDD<String, Row> getSessionid2ActionRDD(JavaRDD<Row> actionRDD) {
		return actionRDD.mapPartitionsToPair(new PairFlatMapFunction<Iterator<Row>, String, Row>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Iterable<Tuple2<String, Row>> call(Iterator<Row> iterator)
					throws Exception {
				List<Tuple2<String, Row>> list = new ArrayList<Tuple2<String, Row>>();
				
				while(iterator.hasNext()) {
					Row row = iterator.next();
					System.out.println("sessionid "+row.getString(2)+"------"+row.toString());
					list.add(new Tuple2<String, Row>(row.getString(2), row));  
				}
				
				return list;
			}
			
		});
	}
	
	
	
	private static JavaPairRDD<String, String> aggregateBySession(
					JavaSparkContext sc,
					SQLContext sqlContext, 
					JavaPairRDD<String, Row> sessinoid2actionRDD) {
				// 对行为数据按session粒度进行分组
				JavaPairRDD<String, Iterable<Row>> sessionid2ActionsRDD = 
						sessinoid2actionRDD.groupByKey();
				/*sessionid2ActionsRDD.foreach(x->{
					String sessionid = x._1;
					System.out.println(sessionid);
					Iterator<Row> iterator = x._2.iterator();
					while(iterator.hasNext()){
						Row	row = iterator.next();
						System.out.println("-------------"+row);
					}
				});*/
				
				
				// 对每一个session分组进行聚合，将session中所有的搜索词和点击品类都聚合起来
				// 到此为止，获取的数据格式，如下：<userid,partAggrInfo(sessionid,searchKeywords,clickCategoryIds)>
				JavaPairRDD<Long, String> userid2PartAggrInfoRDD = sessionid2ActionsRDD.mapToPair(
						
						new PairFunction<Tuple2<String,Iterable<Row>>, Long, String>() {
							
							private static final long serialVersionUID = 1L;
				
							@Override
							public Tuple2<Long, String> call(Tuple2<String, Iterable<Row>> tuple)
									throws Exception {
								String sessionid = tuple._1;
								Iterator<Row> iterator = tuple._2.iterator();
								
								StringBuffer searchKeywordsBuffer = new StringBuffer("");
								StringBuffer clickCategoryIdsBuffer = new StringBuffer("");
								
								Long userid = null;
								
								// session的起始和结束时间
								Date startTime = null;
								Date endTime = null;
								// session的访问步长
								int stepLength = 0;
								
								// 遍历session所有的访问行为
								while(iterator.hasNext()) {
									// 提取每个访问行为的搜索词字段和点击品类字段
									Row row = iterator.next();
									if(userid == null) {
										userid = row.getLong(1);
									}
									String searchKeyword = row.getString(5);
									Long clickCategoryId = row.getLong(6);
									
									if(StringUtils.isNotEmpty(searchKeyword)) {
										if(!searchKeywordsBuffer.toString().contains(searchKeyword)) {
											searchKeywordsBuffer.append(searchKeyword + ",");  
										}
									}
									if(clickCategoryId != null) {
										if(!clickCategoryIdsBuffer.toString().contains(
												String.valueOf(clickCategoryId))) {   
											clickCategoryIdsBuffer.append(clickCategoryId + ",");  
										}
									}
									
									// 计算session开始和结束时间
									Date actionTime = DateUtils.parseTime(row.getString(4));
									
									if(startTime == null) {
										startTime = actionTime;
									}
									if(endTime == null) {
										endTime = actionTime;
									}
									
									if(actionTime.before(startTime)) {
										startTime = actionTime;
									}
									if(actionTime.after(endTime)) {
										endTime = actionTime;
									}
									
									// 计算session访问步长
									stepLength++;
								}
								
								String searchKeywords = StringUtils.trimComma(searchKeywordsBuffer.toString());
								String clickCategoryIds = StringUtils.trimComma(clickCategoryIdsBuffer.toString());
								
								// 计算session访问时长（秒）
								long visitLength = (endTime.getTime() - startTime.getTime()) / 1000; 

								String partAggrInfo = Constants.FIELD_SESSION_ID + "=" + sessionid + "|"
										+ Constants.FIELD_SEARCH_KEYWORDS + "=" + searchKeywords + "|"
										+ Constants.FIELD_CLICK_CATEGORY_IDS + "=" + clickCategoryIds + "|"
										+ Constants.FIELD_VISIT_LENGTH + "=" + visitLength + "|"
										+ Constants.FIELD_STEP_LENGTH + "=" + stepLength + "|"
										+ Constants.FIELD_START_TIME + "=" + DateUtils.formatTime(startTime);    
								
								return new Tuple2<Long, String>(userid, partAggrInfo);
							}
							
						});
				
				// 查询所有用户数据，并映射成<userid,Row>的格式
				String sql = "select * from user_info";  
				JavaRDD<Row> userInfoRDD = sqlContext.sql(sql).javaRDD();
				
				JavaPairRDD<Long, Row> userid2InfoRDD = userInfoRDD.mapToPair(
						
						new PairFunction<Row, Long, Row>() {

							private static final long serialVersionUID = 1L;

							@Override
							public Tuple2<Long, Row> call(Row row) throws Exception {
								return new Tuple2<Long, Row>(row.getLong(0), row);
							}
							
						});

				JavaPairRDD<Long, Tuple2<String, Row>> userid2FullInfoRDD = 
						userid2PartAggrInfoRDD.join(userid2InfoRDD);
				
				// 对join起来的数据进行拼接，并且返回<sessionid,fullAggrInfo>格式的数据
				JavaPairRDD<String, String> sessionid2FullAggrInfoRDD = userid2FullInfoRDD.mapToPair(
						
						new PairFunction<Tuple2<Long,Tuple2<String,Row>>, String, String>() {

							private static final long serialVersionUID = 1L;

							@Override
							public Tuple2<String, String> call(
									Tuple2<Long, Tuple2<String, Row>> tuple)
									throws Exception {
								String partAggrInfo = tuple._2._1;
								Row userInfoRow = tuple._2._2;
								
								String sessionid = StringUtils.getFieldFromConcatString(
										partAggrInfo, "\\|", Constants.FIELD_SESSION_ID);
								
								int age = userInfoRow.getInt(3);
								String professional = userInfoRow.getString(4);
								String city = userInfoRow.getString(5);
								String sex = userInfoRow.getString(6);
								
								String fullAggrInfo = partAggrInfo + "|"
										+ Constants.FIELD_AGE + "=" + age + "|"
										+ Constants.FIELD_PROFESSIONAL + "=" + professional + "|"
										+ Constants.FIELD_CITY + "=" + city + "|"
										+ Constants.FIELD_SEX + "=" + sex;
								
								return new Tuple2<String, String>(sessionid, fullAggrInfo);
							}
							
						});
	
				return sessionid2FullAggrInfoRDD;
			}
	
	
}

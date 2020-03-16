package org.clm.hbase.sparkonhbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapred.TableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class SparkOnHbaseTest {
	public static void main(String[] args) {
		SparkOnHbaseTest test = new SparkOnHbaseTest();
		//单条插入
		//test.sparkonhbase_dantiao();
		
		//批量写入
		//test.sparkonhbase_piliang();
		
		//mapreduce 批量写入    和上面两个比最快
		//test.sparkonhbase_mapreduce();
		
		//bulk
		test.sparkonhbase_bulk();
		
	}
	
	public void sparkonhbase_bulk() {
		String tableName = "student3";
		String[] columnFamilys = { "info", "course"};
		SparkConf sparkconf = new SparkConf();
		sparkconf.setAppName("example");
		JavaSparkContext jsc = new JavaSparkContext(sparkconf);
		
		Configuration hbaseconfig = HBaseConfiguration.create();
		hbaseconfig.set(TableInputFormat.INPUT_TABLE, tableName);
		
		try {
			Connection connection = ConnectionFactory.createConnection(hbaseconfig);
			Admin admin = connection.getAdmin();
			
			TableName name = TableName.valueOf(tableName);
	        if (admin.tableExists(name)) {
	            System.out.println("表 "+tableName+" 已存在！");
	            System.exit(0);
	        } else {
	            // 新建一个students表的描述
	            HTableDescriptor tableDesc = new HTableDescriptor(name);
	            // 在描述里添加列族
	            for (String columnFamily : columnFamilys) {
	            	HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(columnFamily);
	                tableDesc.addFamily(hColumnDescriptor);
	            }
	            // 根据配置好的描述建表
	            admin.createTable(tableDesc);
	            System.out.println("创建表 "+tableName+" 成功!");
	        }
			
			HTable table = new HTable(hbaseconfig, tableName);
			Job job = Job.getInstance(hbaseconfig);
			job.setOutputKeyClass(ImmutableBytesWritable.class);
			job.setOutputValueClass(KeyValue.class);
			
			HFileOutputFormat.configureIncrementalLoad(job, table);
			
			List<String> list = new ArrayList<String>();
			list.add("1,aaa,10");
			list.add("2,bbb,11");
			list.add("3,ccc,12");
			JavaRDD<String> rdd = jsc.parallelize(list);
			
			JavaPairRDD<ImmutableBytesWritable, KeyValue> maprdd = rdd.mapToPair(line->{
				String[] split = line.split(",");
				KeyValue kv = new KeyValue(Bytes.toBytes(split[0]), Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(split[1]));
				return new Tuple2<ImmutableBytesWritable, KeyValue>(new ImmutableBytesWritable(Bytes.toBytes(split[0])), kv);
			});
			maprdd.saveAsNewAPIHadoopFile("/hrds/mashibing/testdata/test1",ImmutableBytesWritable.class, KeyValue.class, HFileOutputFormat2.class,hbaseconfig);
			LoadIncrementalHFiles bulkLoader  = new LoadIncrementalHFiles(hbaseconfig);
			bulkLoader.doBulkLoad(new Path("/hrds/mashibing/testdata/test1"), table);
			jsc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//mapreduce 批量写入    最快
	public void sparkonhbase_mapreduce() {
		SparkConf conf = new SparkConf();
		conf.setAppName("example");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		
		Configuration hbaseconfig = HBaseConfiguration.create();
		JobConf jobConf = new JobConf(hbaseconfig);
		jobConf.set("hbase.zookeeper.quorum", "10.71.4.51,10.71.4.52,10.71.4.53");
		jobConf.set("hbase.zookeeper.property.clientPort", "2181");
		jobConf.set("zookeeper.znode.parent", "/hbase");
		jobConf.set(TableOutputFormat.OUTPUT_TABLE, "student2");
	    jobConf.setOutputFormat(TableOutputFormat.class);
		
	    List<String> list = new ArrayList<>();
		list.add("1,aaa,10");
		list.add("2,bbb,11");
		list.add("3,ccc,12");
		JavaRDD<String> rdd = jsc.parallelize(list);
		
		rdd.mapToPair(line->{
			String[] split = line.split(",");
			Put put = new Put(Bytes.toBytes(split[0]));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(split[1]));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(split[2]));
			return new Tuple2<ImmutableBytesWritable, Put>(new ImmutableBytesWritable(Bytes.toBytes(split[0])), put);
		}).saveAsHadoopDataset(jobConf);
		
		jsc.stop();
	}
	
	//批量写入
	public void sparkonhbase_piliang() {
		SparkConf conf = new SparkConf();
		conf.setAppName("example");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		List<String> list = new ArrayList<>();
		list.add("1,aaa,10");
		list.add("2,bbb,11");
		list.add("3,ccc,12");
		JavaRDD<String> rdd = jsc.parallelize(list);
		
		JavaRDD<Put> maprdd = rdd.map(line->{
			String[] split = line.split(",");
			Put put = new Put(Bytes.toBytes(split[0]));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(split[1]));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(split[2]));
			return put;
		});
		
		maprdd.foreachPartition(iterator->{
			Configuration hbaseconfig = HBaseConfiguration.create();
			JobConf jobConf = new JobConf(hbaseconfig);
			jobConf.set("hbase.zookeeper.quorum", "10.71.4.51,10.71.4.52,10.71.4.53");
			jobConf.set("hbase.zookeeper.property.clientPort", "2181");
			jobConf.set("zookeeper.znode.parent", "/hbase");
		    jobConf.setOutputFormat(TableOutputFormat.class);
			HTable hTable = new HTable(jobConf, TableName.valueOf("student2"));
			hTable.put(IteratorUtils.toList(iterator));
		});
		
	}
	
	
	
	//单条插入
	public void sparkonhbase_dantiao() {
		SparkConf conf = new SparkConf();
		conf.setAppName("example");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		List<String> list = new ArrayList<>();
		list.add("1,aaa,10");
		list.add("2,bbb,11");
		list.add("3,ccc,12");
		JavaRDD<String> rdd = jsc.parallelize(list);
		
		rdd.foreachPartition(iterator->{
			Configuration hbaseconfig = HBaseConfiguration.create();
			hbaseconfig.set("hbase.zookeeper.quorum", "10.71.4.51,10.71.4.52,10.71.4.53");
			hbaseconfig.set("hbase.zookeeper.property.clientPort", "2181");
			Connection connection = ConnectionFactory.createConnection(hbaseconfig);
			Table table = connection.getTable(TableName.valueOf("student2"));
			
			while(iterator.hasNext()) {
				String[] split = iterator.next().split(",");
				Put put = new Put(Bytes.toBytes(split[0]));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(split[1]));
				put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(split[2]));
				table.put(put);
			}
		});
		
	}
}

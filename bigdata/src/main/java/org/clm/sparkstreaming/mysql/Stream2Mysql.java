package org.clm.sparkstreaming.mysql;

import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.clm.sparkstreaming.jdbc.ConnectionPool;
import scala.Tuple2;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;

public class Stream2Mysql {
	
	public static void main(String[] args) {
		Stream2Mysql test = new Stream2Mysql();
		//sparkstreaming socket案例
		//test.socketStreamTest();
		
		//sparkstreaming mysql
		test.stream2MysqlTest();
	}
	
	public void stream2MysqlTest() {
		try {
			/*Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/mytable?characterEncoding=utf8&useSSL=true";
			String user = "root";
			String password = "root";
			final Connection conn = DriverManager.getConnection(url, user, password);;*/

			JavaStreamingContext jssc = new JavaStreamingContext("local[2]", "stream2mysql", new Duration(1000));
			JavaReceiverInputDStream<String> socketTextStream = jssc.socketTextStream("localhost", 9999);
			//jssc.checkpoint("D:\\testdata\\checkpoint");

			JavaDStream<String> word = socketTextStream.flatMap(line -> {
				return Arrays.asList(line.split(" ")).iterator();
			});
			JavaPairDStream<String, Integer> pairs = word.mapToPair(line ->{
				return new Tuple2<>(line,1);
			});
			JavaPairDStream<String, Integer> wordcounts = pairs.reduceByKey((x,y)->{
				return x+y;
			});
			wordcounts.print();


			wordcounts.foreachRDD(rdd->{
				rdd.foreachPartition(Iterator->{
					Connection conn = ConnectionPool.getConnection();
					while(Iterator.hasNext()) {
						Tuple2<String, Integer> tuple = Iterator.next();
						String w = tuple._1;
						long c = tuple._2;

						String sql = "insert into wc_table(word,count) values ('"+w+"',"+c+")";
						//String sql = "insert into wc_table(word,count) values ('hello',1)";
						Statement createStatement = conn.createStatement();
						createStatement.executeUpdate(sql);
					}
					ConnectionPool.returnConnection(conn);
				});
			});

			jssc.start();
			jssc.awaitTermination();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void socketStreamTest() {
		/*SparkConf conf = new SparkConf().setAppName("mysqltest").setMaster("local");
		SparkSession spark = SparkSession.builder().config(conf).getOrCreate();*/

		JavaStreamingContext jssc = new JavaStreamingContext("local[2]", "sparkstream", new Duration(1000));
		JavaReceiverInputDStream<String> socketTextStream = jssc.socketTextStream("localhost", 9999);
		
		JavaDStream<String> word = socketTextStream.flatMap(line -> {
			return Arrays.asList(line.split(" ")).iterator();
		});
		JavaPairDStream<String, Integer> pairs = word.mapToPair(line ->{
			return new Tuple2<>(line,1);
		});
		JavaPairDStream<String, Integer> wordcounts = pairs.reduceByKey((x,y)->{
			return x+y;
		});
		wordcounts.print();
		
		try {
			jssc.start();
			jssc.awaitTermination();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}

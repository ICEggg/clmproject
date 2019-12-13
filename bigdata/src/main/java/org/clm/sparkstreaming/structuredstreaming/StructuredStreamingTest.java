package org.clm.sparkstreaming.structuredstreaming;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.ForeachWriter;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.StructType;

public class StructuredStreamingTest {
	public static void main(String[] args) {
		StructuredStreamingTest sst = new StructuredStreamingTest();
		//socket test
		//sst.test();
		
		//读csv流到本地文件夹
		//sst.writecsvtolocal();
		
		sst.writetokafka();
		
		//sst.readkafka();
	}
	
	public void writecsvtolocal(){
		SparkSession spark = SparkSession.builder().master("local[*]").appName("example").getOrCreate();
		//JavaSparkContext jssc = new JavaSparkContext(spark.sparkContext());
		
		StructType userSchema = new StructType()
				.add("id", "integer")
				.add("name", "string")
				.add("age", "integer")
				.add("sex", "string");
		Dataset<Row> df = spark
		  .readStream()
		  .schema(userSchema)      // Specify schema of the csv files
		  .csv("D:\\testdata\\csvfile");
		
		/*StreamingQuery query = df.writeStream()
			  .outputMode(OutputMode.Append())
			  .option("checkpointLocation", "D:\\testdata\\structuredstreaming_checkpoint")
			  .format("csv")
			  .start("D:\\testdata\\csv");*/
		
		//打印流
		StreamingQuery query = df.writeStream()
			  .outputMode(OutputMode.Append())
			 // .option("checkpointLocation", "D:\\testdata\\structuredstreaming_checkpoint")
			  .format("console")
			  .start();

		try {
			query.awaitTermination();
		} catch (StreamingQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writetokafka() {
		SparkSession spark = SparkSession.builder().master("local[*]").appName("example").getOrCreate();
		JavaSparkContext jssc = new JavaSparkContext(spark.sparkContext());
		
		//csv 没成功
		/*StructType userSchema = new StructType()
				.add("id", "integer")
				.add("name", "string")
				.add("age", "integer")
				.add("sex", "string");
		Dataset<Row> df = spark
		  .readStream()
		  .schema(userSchema)
		  .csv("D:\\testdata\\csvfile");*/
		
		Dataset<String> df = spark
				  .readStream()
				  .textFile("D:\\testdata\\csvfile");
		
		StreamingQuery ds = df
		  /*.selectExpr("CAST(id AS integer) as id","CAST(name AS string) as name",
				  	  "CAST(age AS integer) as age","CAST(sex AS string) as sex")*/
		  .writeStream()
		  .format("kafka")
		  .outputMode(OutputMode.Append())
		  .option("checkpointLocation", "D:\\testdata\\structuredstreaming_checkpoint")
		  .option("kafka.bootstrap.servers", "172.168.0.101:9092,172.168.0.102:9092,172.168.0.103:9092")
		  .option("topic", "clmtopic")
		  .start();

		try {
			ds.awaitTermination();
		} catch (StreamingQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readkafka() {
		SparkSession spark = SparkSession.builder().master("local[*]").appName("example").getOrCreate();
		
		// Subscribe to 1 topic
		Dataset<Row> df1 = spark
		  .readStream()
		  .format("kafka")
		  .option("kafka.bootstrap.servers", "172.168.0.101:9092,172.168.0.102:9092,172.168.0.103:9092")
		  .option("subscribe", "clmtopic")
		  .load();
			
		StreamingQuery query = df1.selectExpr("CAST(value AS STRING)")
			//.as(Encoders.STRING())
			.writeStream()
			//.outputMode(OutputMode.Append())
			.format("console")
			.start();
		try {
			query.awaitTermination();
		} catch (StreamingQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*Dataset<Row> df2 = spark
		  .readStream()
		  .format("kafka")
		  .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
		  .option("subscribe", "topic1,topic2")
		  .load();
		df2.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");

		// Subscribe to a pattern
		Dataset<Row> df3 = spark
		  .readStream()
		  .format("kafka")
		  .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
		  .option("subscribePattern", "topic.*")
		  .load();
		df3.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");*/
	}
	
	public void test() {
		SparkSession spark = SparkSession.builder().master("local[*]").appName("example").getOrCreate();
		
		Dataset<Row> lines = spark
				  .readStream()
				  .format("socket")
				  .option("host", "localhost")
				  .option("port", 9999)
				  .load();

		System.out.println(lines.isStreaming());    // Returns True for DataFrames that have streaming sources

		lines.printSchema();

		// Read all the csv files written atomically in a directory
		StructType userSchema = new StructType().add("name", "string").add("age", "integer");
		Dataset<Row> csvDF = spark
		  .readStream()
		  .option("sep", ";")
		  .schema(userSchema)      // Specify schema of the csv files
		  .csv("D:\\testdata\\csvfile\\csv.txt");    // Equivalent to format("csv").load("/path/to/directory")
		
		System.out.println(csvDF.isStreaming());    // Returns True for DataFrames that have streaming sources

		csvDF.printSchema();
		
		/*Dataset<String> words = lines
		  .as(Encoders.STRING())
		  .flatMap((FlatMapFunction<String, String>) x -> Arrays.asList(x.split(" ")).iterator(), Encoders.STRING());

		Dataset<Row> wordCounts = words.groupBy("value").count();
		
		StreamingQuery query = wordCounts.writeStream()
				  .outputMode(OutputMode.Complete())
				  .format("console")
				  .start();

		try {
			query.awaitTermination();
		} catch (StreamingQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}

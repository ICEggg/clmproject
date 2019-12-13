package org.clm.sparkstreaming.udf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.parquet.filter2.predicate.Operators.And;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class SparkUdf {
	public static void main(String[] args) {
		SparkUdf udf = new SparkUdf();
		//udf
		//udf.udf_function();
	    
		//保存到持久表   parquet
		//udf.persist_table();
		
		//hive
		udf.hive_table();
	}
	
	public void hive_table() {
		SparkConf conf = new SparkConf()
				.setAppName("Example")
				.set("spark.sql.warehouse.dir", "hdfs://nameservice1//user//hive//warehouse")
				.setMaster("local[2]");
	    SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate(); 
	    JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
	    
	    //spark.sql("create database hivedb");
	    //spark.sql("create table student (id int ,name varchar(60),age int)");
	    
	    spark.sql("show databases").show();
	    //spark.sql("select * from s001_z002_ounit limit 10").show();
	}
	
	
	public void persist_table() {
		SparkConf conf = new SparkConf().setAppName("Java UDF Example").setMaster("local");
	    SparkSession spark = SparkSession.builder().config(conf).getOrCreate(); 
	    JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
	    
	    /*spark.read().json("D:\\testdata\\jsonfile\\json.txt")
	    .write()
	    .format("parquet")
	    .save("D:\\testdata\\jsonfile\\testparquet");*/
	    
	    /*Dataset<Row> row = spark.read().parquet("D:\\testdata\\jsonfile\\testparquet");
	    row.map(row2 -> "Name: " + row2.getString(0),
	    		Encoders.STRING()).show();*/
	    
	    String schemaString = "id name sex";
	    List<String> list = new ArrayList<>();
	    list.add("1,a,男");
	    list.add("2,b,男");
	    list.add("3,c,女");
	    list.add("4,d,女");
	    JavaRDD<String> rdd1 = sc.parallelize(list);
	    JavaRDD<Row> rdd2 = rdd1.map(line->{
	    	String[] split = line.split(",");
	    	Row row = RowFactory.create(split[0], split[1], split[2]);
	    	return row;
	    });
	    
	    
	    List<StructField> StructFieldlist = new ArrayList<>();
	    for (String str: schemaString.split(" ")) {
			StructField createStructField = DataTypes.createStructField(str, DataTypes.StringType, true);
			StructFieldlist.add(createStructField);
		}
	    
	    
	    StructType schema = DataTypes.createStructType(StructFieldlist);
	    spark.createDataFrame(rdd2, schema)
	    	 .write()
	    	 .mode(SaveMode.Overwrite)
	    	 .partitionBy("sex","name")
	    	 .parquet("D:\\testdata\\tmpparquet");
	    
	    //读取parquet分区
		//spark.read().parquet("D:\\testdata\\tmpparquet\\").filter("sex == '男' And name == 'a'").show();
	}
	
	public void udf_function() {
		SparkConf conf = new SparkConf().setAppName("Java UDF Example").setMaster("local");
	    SparkSession spark = SparkSession.builder().config(conf).getOrCreate(); 
	 
	    Dataset<Row> ds = spark.read().json("D:\\testdata\\jsonfile\\json.txt");
	    ds.createOrReplaceTempView("citytemps");
	   
	    // Register the UDF with our SparkSession 
	    spark.udf().register("add", new UDF1<String, Integer>() {
	      @Override
	      public Integer call(String degreesCelcius) {
	        return Integer.parseInt(degreesCelcius)*10;
	      }
	    }, DataTypes.IntegerType);
	    
	    spark.sql("SELECT add(age) from citytemps").show();
	}
	
}

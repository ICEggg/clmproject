package org.clm.sparkstreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;  

public class MyTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("example").setMaster("local");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		
		JavaRDD<String> filerdd = jsc.textFile("D:\\testdata\\adult_result.txt");
		System.out.println(filerdd.collect());
		
	}
	
}

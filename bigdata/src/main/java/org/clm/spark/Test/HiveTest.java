package org.clm.spark.Test;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.*;

import java.util.Arrays;
import java.util.List;

public class HiveTest {
    public static void main(String[] args) {
        HiveTest test = new HiveTest();
        //spark to hive
        //test.hiveway();

        //test.dftest();

        test.dftest2();
    }

    public void dftest2(){
        SparkSession spark = SparkSession.builder().
                master("local").
                appName("test").
                getOrCreate();
        RDD<String> rdd = spark.sparkContext().textFile("myspark/testfile/wc", 2);
        Dataset<Row> df = spark.read().csv("myspark/testfile/wc").toDF("word","num");

        //df.printSchema();
        //df.show();
    }

    public void dftest(){
        SparkSession spark = SparkSession.builder().
                master("local").
                appName("test").
                getOrCreate();
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        Encoder<Integer> anInt = Encoders.INT();
        Dataset<Integer> df = spark.createDataset(list, anInt);
        Dataset<Integer> df2 = df.map((MapFunction<Integer, Integer>) value -> value + 1, anInt);
    }

    public void hiveway(){
        SparkSession spark = SparkSession.builder().
                master("local").
                appName("test").
                config("spark.sql.warehouse.dir", "/user/hive/warehouse").
                enableHiveSupport().
                getOrCreate();
        spark.sql("use clmhivedb");
        spark.sql("create table stoh (id int ,name varchar(20))");
        spark.sql("insert into stoh values (1,'aaa')");
        spark.sql("insert into stoh values (2,'bbb')");
        Dataset<Row> sqldf = spark.sql("select * from stoh");
        sqldf.show();
    }
}

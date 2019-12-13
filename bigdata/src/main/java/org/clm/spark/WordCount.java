package org.clm.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class WordCount {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<String> filerdd = jsc.textFile("D:\\testdata\\testfile.txt");
        JavaRDD<String> flatmaprdd = filerdd.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.asList(line.split("\\|")).iterator();
            }
        });

        JavaPairRDD<String, Integer> maprdd = flatmaprdd.mapToPair(line -> {
            return new Tuple2<>(line, 1);
        });

        JavaPairRDD<String, Integer> reducerdd = maprdd.reduceByKey((x, y) -> {
            return x + y;
        });

        reducerdd.foreach(line->{
            System.out.println(line);
        });
    }
}

package org.clm.spark.Test;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 1    F    170
 * 2    M    178
 * 3    M    174
 * 4    F    165
 *
 * 男性总数、女性总数、男性最高身高、女性最高身高、男性最低身高、女性最低身高
 */
public class SparkPractice {
    public static void main(String[] args) {
        SparkPractice sp = new SparkPractice();
        SparkConf conf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        //sp.createdata(jsc);
        sp.practice(jsc);

    }

    public void practice(JavaSparkContext jsc){
        JavaRDD<String> filerdd = jsc.textFile("D:\\testdata\\student");
        JavaRDD<String> ManRdd = filerdd.filter(line -> {
            if (line.contains("M")) {
                return true;
            }
            return false;
        });
        System.out.println("男性总数="+ManRdd.count());

        /*JavaPairRDD<String, String> sortrdd = ManRdd.mapToPair(line -> {
            String[] split = line.split(" ");
            return new Tuple2<>(split[2], line);
        }).sortByKey(true);
        sortrdd.foreach(line-> System.out.println(line));*/

        List<String> minManRdd = ManRdd.sortBy(line -> {
            return line.split(" ")[2];
        }, true, 1).take(1);
        System.out.println(minManRdd.get(0));
    }

    public void createdata( JavaSparkContext jsc){
        List<String> array = new ArrayList<>();
        for (int i = 0; i <1000; i++) {
            double height = Math.random() * 230;
            String gender = getRandomGender();
            if (height <100 && gender == "M") {
                height = height+100;
            };
            if (height <100 && gender == "F") {
                height = height+50;
            };
            array.add(i+" "+gender+" "+height);
        }
        JavaRDD<String> rdd = jsc.parallelize(array);
        /*File file = new File("D:\\testdata\\student");
        if(file.exists()){
            file.delete();
        }*/

        rdd.saveAsTextFile("D:\\testdata\\student");
    }

    public String getRandomGender(){
        int randNum = (int)Math.random()*2;
        System.out.println(randNum);
        if (randNum==1) {
            return "M";
        } else {
            return "F";
        }
    }
}

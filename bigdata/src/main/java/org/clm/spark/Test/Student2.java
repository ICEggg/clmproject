package org.clm.spark.Test;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.util.LongAccumulator;
import org.clm.spark.secondsort.MySort;
import scala.Tuple2;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 12 张三 25 男 chinese 50
 * 12 张三 25 男 math 60
 * 12 张三 25 男 english 70
 * 12 李四 20 男 chinese 50
 * 12 李四 20 男 math 50
 * 12 李四 20 男 english 50
 * 12 王芳 19 女 chinese 70
 * 12 王芳 19 女 math 70
 * 12 王芳 19 女 english 70
 * 13 张大三 25 男 chinese 60
 * 13 张大三 25 男 math 60
 */
public class Student2 implements Serializable {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        Student2 student = new Student2();
        //单个人平均成绩是多少
        //student.singlepersonscore(jsc);

        //全校语文成绩最高分
        //student.max_chinese(jsc);

        //aggregate测试 求平均数
        //student.aggregatetest(jsc);

        //aggregatebykey
        //student.aggregatebykeytest(jsc);

        //二次排序
        //student.secondsort(jsc);


        //现在要对最近7天的日志进行统计,统计结果格式如下,key(date(日期),hour(时间),site(网站))
        //value:(pv (访问次数),uv(独立访问人数,相同的访客id去重))
        //student.combineByKeytest(jsc);

        //student.accumulatortest(jsc);

    }

    public void accumulatortest(JavaSparkContext jsc){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        JavaRDD<Integer> rdd = jsc.parallelize(list);
        LongAccumulator accumulator = jsc.sc().longAccumulator();

        JavaRDD<Integer> maprdd = rdd.map(x -> {
            accumulator.add(1);
            return x + 1;
        });
        maprdd.foreach(x-> System.out.println(x));
        System.out.println(accumulator.value());

    }

    public void combineByKeytest(JavaSparkContext jsc){
        JavaRDD<String> filerdd = jsc.textFile("myspark/testfile/student.txt");
        JavaPairRDD<String, String> maprdd = filerdd.mapToPair(x -> {
            String[] split = x.split(" ");
            String name = split[1];
            String score = split[5];
            return new Tuple2<>(name, score);
        });
        JavaPairRDD<String, Object> combinerdd = maprdd.combineByKey(new Function<String, Object>() {
            @Override
            public Tuple2 call(String v1) throws Exception {
                return new Tuple2<>(Integer.parseInt(v1),1);
            }
        }, new Function2<Object, String, Object>() {
            @Override
            public Tuple2 call(Object v1, String v2) throws Exception {
                Tuple2<Integer, Integer> tuple1 = (Tuple2) v1;
                return new Tuple2<>(tuple1._1 + Integer.parseInt(v2),tuple1._2 + 1);
            }
        }, new Function2<Object, Object, Object>() {
            @Override
            public Tuple2 call(Object v1, Object v2) throws Exception {
                Tuple2<Integer, Integer> tuple1 = (Tuple2) v1;
                Tuple2<Integer, Integer> tuple2 = (Tuple2) v2;
                return new Tuple2<>(tuple1._1 + tuple2._1,tuple1._2 + tuple2._2);
            }
        });
        //combinerdd.foreach(x-> System.out.println(x));
        JavaPairRDD<String, Integer> maprdd2 = combinerdd.mapToPair(x -> {
            Tuple2<Integer, Integer> tuple = (Tuple2) x._2;
            return new Tuple2<>(x._1, tuple._1 / tuple._2);
        });
        maprdd2.foreach(x-> System.out.println(x));
        jsc.close();
    }

    //二次排序
    public void secondsort(JavaSparkContext jsc){
        JavaRDD<String> filerdd = jsc.textFile("myspark/testfile/compfile");

        JavaPairRDD maprdd = filerdd.mapToPair(new PairFunction<String, MySort, Object>() {
            @Override
            public Tuple2<MySort, Object> call(String s) throws Exception {
                String[] split = s.split(" ");
                return new Tuple2<>(new MySort(Integer.parseInt(split[0]),Integer.parseInt(split[1])),s);
            }
        });

        JavaRDD redultrdd = maprdd.sortByKey().map(x -> {
            Tuple2<MySort, Object> tuple = (Tuple2<MySort, Object>) x;
            return tuple._2;
        });
        redultrdd.foreach(x-> System.out.println(x));

        jsc.close();
    }


    public void aggregatebykeytest(JavaSparkContext jsc){
        JavaRDD<String> filerdd = jsc.textFile("myspark/testfile/student.txt");

        JavaPairRDD<String, Integer> rdd = filerdd.mapToPair(x -> {
            String[] split = x.split(" ");
            return new Tuple2<>(split[1], new Tuple2<>(split[5], 1));
        }).aggregateByKey(new Tuple2<>(0,0),(v1,v2)->{
            return new Tuple2<Integer, Integer>(Integer.valueOf(v1._1) + Integer.valueOf(v2._1), Integer.valueOf(v1._2) + Integer.valueOf(v2._2));
        },(v1,v2)->{
            return new Tuple2<>(Integer.valueOf(v1._1 + v2._1), Integer.valueOf(v1._2 + v2._2));
        }).mapToPair(x -> {
            int avg = x._2._1 / x._2._2;
            return new Tuple2<>(x._1, avg);
        });;

        /*JavaPairRDD<String, Integer> rdd = filerdd.mapToPair(x -> {
            String[] split = x.split(" ");
            return new Tuple2<>(split[1], new Tuple2<>(split[5], 1));
        }).aggregateByKey(new Tuple2<>(0, 0), new Function2<Tuple2<Integer, Integer>, Tuple2<String, Integer>, Tuple2<Integer, Integer>>() {
            @Override
            public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> v1, Tuple2<String, Integer> v2) throws Exception {
                return new Tuple2<Integer, Integer>(Integer.valueOf(v1._1) + Integer.valueOf(v2._1), Integer.valueOf(v1._2) + Integer.valueOf(v2._2));
            }
        }, new Function2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>() {
            @Override
            public Tuple2<Integer, Integer> call(Tuple2<Integer, Integer> v1, Tuple2<Integer, Integer> v2) throws Exception {
                return new Tuple2<>(Integer.valueOf(v1._1 + v2._1), Integer.valueOf(v1._2 + v2._2));
            }
        }).mapToPair(x -> {
            int avg = x._2._1 / x._2._2;
            return new Tuple2<>(x._1, avg);
        });*/
        rdd.foreach(x-> System.out.println(x));
    }

    public void aggregatetest(JavaSparkContext jsc){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        JavaRDD<Integer> rdd = jsc.parallelize(list,2);
        Tuple2<Integer, Integer> resultlist = rdd.aggregate(new Tuple2<Integer, Integer>(0,0), (acc, num) -> {
            return new Tuple2<>(acc._1() + num,acc._2() + 1);
        }, (x, y) -> {
            return new Tuple2<>(x._1 + y._1, x._2 + y._2);
        });
        int avg = resultlist._1/resultlist._2;
        System.out.println(avg);

        /*Integer resultlist = rdd.aggregate(0, new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                v1 += v2;
                return v1;
            }
        }, new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        System.out.println(resultlist);*/

    }


    public void max_chinese(JavaSparkContext jsc){
        JavaRDD<String> filerdd = jsc.textFile("myspark/testfile/student.txt");
        JavaRDD<String> sortrdd = filerdd.filter(line -> {
            String kemu = line.split(" ")[4];
            if (kemu.equals("chinese")) {
                return true;
            }
            return false;
        }).sortBy(line -> {
            return line.split(" ")[5];
        }, true, 1);

        sortrdd.foreach(x->{
            System.out.println(x);
        });
        System.out.println(sortrdd.take(1).get(0));
    }


    public void singlepersonscore(JavaSparkContext jsc){
        JavaRDD<String> filerdd = jsc.textFile("myspark/testfile/student.txt");
        JavaPairRDD<String, String> maprdd = filerdd.mapToPair(line -> {
            return new Tuple2<>(line.split(" ")[1], line.split(" ")[5]+"_1");
        });

        JavaPairRDD<String, Tuple2<String, String>> map1 = maprdd.mapToPair(tuple -> {
            return new Tuple2<>(tuple._1, new Tuple2<>(tuple._2.split("_")[0], tuple._2.split("_")[1]));
        });
        //map1.foreach(x-> System.out.println(x));

        JavaPairRDD<String, Tuple2<String, String>> reduce1 = map1.reduceByKey((x, y) -> {
            Integer score = Integer.valueOf(x._1)+Integer.valueOf(y._1);
            Integer num = Integer.valueOf(x._2)+Integer.valueOf(y._2);
            return new Tuple2<String, String>(String.valueOf(score), String.valueOf(num));
        });

        JavaPairRDD<String, Integer> map2 = reduce1.mapToPair(tuple -> {
            String name = tuple._1;
            Integer score = Integer.valueOf(tuple._2._1);
            Integer num = Integer.valueOf(tuple._2._2);
            return new Tuple2<>(name, score / num);
        });
        map2.foreach(x-> System.out.println(x));
    }

}

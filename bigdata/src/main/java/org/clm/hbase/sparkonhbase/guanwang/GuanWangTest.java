package org.clm.hbase.sparkonhbase.guanwang;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.spark.JavaHBaseContext;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

//运行失败
public class GuanWangTest {
	public static void main(String[] args) {
		SparkConf sparkconf = new SparkConf();
		sparkconf.setAppName("example");
		JavaSparkContext jsc = new JavaSparkContext(sparkconf);

		try {
		  List<byte[]> list = new ArrayList<>();
		  list.add(Bytes.toBytes("1"));
		  list.add(Bytes.toBytes("2"));
		  list.add(Bytes.toBytes("3"));
		  JavaRDD<byte[]> rdd = jsc.parallelize(list);
		  Configuration conf = HBaseConfiguration.create();
		  conf.set("hbase.zookeeper.quorum", "10.71.4.51:2181,10.71.4.52:2181,10.71.4.53:2181");
		  JavaHBaseContext hbaseContext = new JavaHBaseContext(jsc, conf);
		  String tableName = "student2";
		  
		  hbaseContext.foreachPartition(rdd,
		      new VoidFunction<Tuple2<Iterator<byte[]>, Connection>>() {
		   public void call(Tuple2<Iterator<byte[]>, Connection> t)
		        throws Exception {
		    Table table = t._2().getTable(TableName.valueOf(tableName));
		    BufferedMutator mutator = t._2().getBufferedMutator(TableName.valueOf(tableName));
		    while (t._1().hasNext()) {
		      byte[] b = t._1().next();
		      Result r = table.get(new Get(b));
		      if (r.getExists()) {
		       mutator.mutate(new Put(b));
		      }
		    }

		    mutator.flush();
		    mutator.close();
		    table.close();
		   }
		  });
		} finally {
		  jsc.stop();
		}
	}
}

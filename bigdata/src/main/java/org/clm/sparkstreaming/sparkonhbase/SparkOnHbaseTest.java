package org.clm.sparkstreaming.sparkonhbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
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

public class SparkOnHbaseTest {
	public static void main(String[] args) {
		SparkOnHbaseTest sh = new SparkOnHbaseTest();
		sh.guanwang_example();
	}
	
	/*public void mytest(){
		SparkConf sparkconf = new SparkConf().setMaster("local[2]").setAppName("example");
		JavaSparkContext jsc = new JavaSparkContext(sparkconf);
		
		Configuration conf = HBaseConfiguration.create();
		JavaHBaseContext hbaseContext = new JavaHBaseContext(jsc, conf);
		
	}*/
	
	public void guanwang_example() {
		SparkConf sparkconf = new SparkConf().setMaster("local[2]").setAppName("example");
		JavaSparkContext jsc = new JavaSparkContext(sparkconf);

		try {
			  List<byte[]> list = new ArrayList<>();
			  list.add(Bytes.toBytes("1"));
			  list.add(Bytes.toBytes("2"));
			  list.add(Bytes.toBytes("3"));
	
			  JavaRDD<byte[]> rdd = jsc.parallelize(list);
			  Configuration conf = HBaseConfiguration.create();
			  conf.set(HConstants.ZOOKEEPER_QUORUM, "172.168.0.63:2181,172.168.0.64:2181,172.168.0.65:2181");
			  JavaHBaseContext hbaseContext = new JavaHBaseContext(jsc, conf);
	
			  hbaseContext.foreachPartition(rdd, new VoidFunction<Tuple2<Iterator<byte[]>,Connection>>() {
				  private static final long serialVersionUID = 1L;

					@Override
					public void call(Tuple2<Iterator<byte[]>, Connection> t) throws Exception {
						Table table = t._2().getTable(TableName.valueOf("test".getBytes()));
					    BufferedMutator mutator = t._2().getBufferedMutator(TableName.valueOf("test".getBytes()));
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

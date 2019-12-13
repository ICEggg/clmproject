package org.clm.sparkstreaming.sparktokafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.TaskContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Stream2Kafka {
	
	public static void main(String[] args) {
		Stream2Kafka sk = new Stream2Kafka();
		//sparkstreaming 读取kafka
		//sk.kafka2stream();
		
		//指定offset
		//sk.rddtest();
		
		//window
		sk.windowtest();
		
	}
	
	
	public void windowtest() {
		
		JavaStreamingContext jssc = new JavaStreamingContext("local[2]", "stream2kafkatest", new Duration(2000));
		JavaReceiverInputDStream<String> socketTextStream = jssc.socketTextStream("localhost", 9999);
		JavaDStream<String> window1 = socketTextStream.window(new Duration(4000),new Duration(2000));
		//window1.print();
		
//		JavaDStream<String> reduceByWindow = socketTextStream.reduceByWindow((v1,v2)->{
//			return v1+v2;
//		},new Duration(4000),new Duration(2000));
		
		window1.foreachRDD((rdd,time)->{
			
		});

		try {
			jssc.start();
			jssc.awaitTermination();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void rddtest() {
		SparkConf conf = new SparkConf().setAppName("kafka2rdd").setMaster("local[2]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		Map<String, Object> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", "172.168.0.101:9092,172.168.0.102:9092,172.168.0.103:9092");
		kafkaParams.put("key.deserializer", StringDeserializer.class);
		kafkaParams.put("value.deserializer", StringDeserializer.class);
		kafkaParams.put("group.id", "group1");
		kafkaParams.put("auto.offset.reset", "latest");//earliest
		kafkaParams.put("enable.auto.commit", false);
		OffsetRange[] offsetRanges = {
		  // topic, partition, inclusive starting offset, exclusive ending offset
		  OffsetRange.create("clmtopic2", 0, 0, 3),
		  OffsetRange.create("clmtopic2", 1, 0, 3)
		};

		JavaRDD<ConsumerRecord<String, String>> rdd = KafkaUtils.createRDD(
				sc,
		  kafkaParams,
		  offsetRanges,
		  LocationStrategies.PreferConsistent()
		);
		
		rdd.foreachPartition(iterator ->{
			while(iterator.hasNext()){
				ConsumerRecord<String, String> kafkardd = iterator.next();
				System.out.println("value="+kafkardd.value()+"--"
									+"offset="+kafkardd.offset()+"--"
									+"partition="+kafkardd.partition());
			}
		});
		
	}
	
	public void kafka2stream() {
		JavaStreamingContext jssc = new JavaStreamingContext("local[2]", "stream2kafkatest", new Duration(5000));
		
		Map<String, Object> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", "172.168.0.101:9092,172.168.0.102:9092,172.168.0.103:9092");
		kafkaParams.put("key.deserializer", StringDeserializer.class);
		kafkaParams.put("value.deserializer", StringDeserializer.class);
		kafkaParams.put("group.id", "group1");
		kafkaParams.put("auto.offset.reset", "earliest");
		kafkaParams.put("enable.auto.commit", false);

		Collection<String> topics = Arrays.asList("clmtopic");

		JavaInputDStream<ConsumerRecord<String, String>> stream =
		  KafkaUtils.createDirectStream(
				  jssc,
		    //默认   这将在可用执行程序之间均匀分配分区
		    //LocationStrategies.PreferConsistent(),
		    
		    //执行程序与Kafka代理程序位于同一主机上
		    LocationStrategies.PreferBrokers(),
		    ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
		  );

		//JavaPairDStream<String, String> mapToPair = stream.mapToPair(record -> new Tuple2<>(record.key(), record.value()));
		//mapToPair.print();
		
		stream.foreachRDD(rdd -> {
			  OffsetRange[] offsetRanges = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
			  rdd.foreachPartition(consumerRecords -> {
			    OffsetRange o = offsetRanges[TaskContext.get().partitionId()];
			    System.out.println(
			       o.topic() + " " + o.partition() + " " + o.fromOffset() + " " + o.untilOffset());
			  });
			});
		
		jssc.start();
		try {
			jssc.awaitTermination();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
	
}

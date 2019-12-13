package org.clm.kafka.hbase;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class HbaseKafkaConsumer {
	private static Properties properties = new Properties();
	public HbaseKafkaConsumer(){
		properties.put("bootstrap.servers", "10.71.4.55:9092,10.71.4.56:9092");
		//properties.put("bootstrap.servers", "172.168.0.63:9092,172.168.0.64:9092,172.168.0.65:9092");
        properties.put("group.id", "group1");
        //是否自动提交
        properties.put("enable.auto.commit", "true");
        //自动提交时间
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	}
	
	public static void main(String[] args) {
		HbaseKafkaConsumer hkc = new HbaseKafkaConsumer();
		try {
			hkc.consumer_kafka(properties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void consumer_kafka(Properties properties) throws IOException{
		Long starttime = System.currentTimeMillis();
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("clmtopic"));
        boolean flag = true;
        int num = 0;
        HBaseUtils hbase = new HBaseUtils();
        while (flag) {
        	ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
		    
		    for (ConsumerRecord<String, String> record : records) {
		    	num++;
		        //System.out.printf("offset = %d, value = %s", record.offset(), record.value());
		        //System.out.println("offset = "+record.offset() +"_"+"key = "+record.key()+ "_" +"value = "+ record.value());
		    	hbase.put(record.key()+ "_"+ record.value());
		    }
		    System.out.println("当前批次消费了多少条消息："+num);
        }
        Long endtime = System.currentTimeMillis();
	    System.out.println("时间："+ String.valueOf(endtime - starttime));
	}
}

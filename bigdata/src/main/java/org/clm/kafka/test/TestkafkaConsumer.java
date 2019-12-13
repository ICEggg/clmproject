package org.clm.kafka.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

public class TestkafkaConsumer {
	private static Properties properties = new Properties();
	
	public static void main(String[] args) {
		TestkafkaConsumer testkafkaConsumer = new TestkafkaConsumer();
		//普通消费
		testkafkaConsumer.consumer_kafka(properties);
		
		//指定topic的某个分区消费
		//testkafkaConsumer.consumer_kafka_partition(properties);
		
		//从指定topic的partition的offset消费
		//testkafkaConsumer.consumer_kafka_offset(properties);
	}
	
	public TestkafkaConsumer(){
		properties.put("bootstrap.servers", "10.71.4.55:9092,10.71.4.56:9092");
		//properties.put("bootstrap.servers", "172.168.0.63:9092,172.168.0.64:9092,172.168.0.65:9092");
        properties.put("group.id", "group-9");
        //是否自动提交
        properties.put("enable.auto.commit", "true");
        //自动提交时间
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	}
	
	
	
	//普通消费
	public void consumer_kafka(Properties properties){
		Long starttime = System.currentTimeMillis();
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("clmtopic"));
        boolean flag = true;
        int num = 0;
        while (flag) {
        	ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
		    
		    for (ConsumerRecord<String, String> record : records) {
		    	num++;
		        //System.out.printf("offset = %d, value = %s", record.offset(), record.value());
		        System.out.println("offset = "+record.offset() +"_"+"key = "+record.key()+ "_" +"value = "+ record.value());
		    }
		    System.out.println("当前批次消费了多少条消息："+num);
		    //kafkaConsumer.commitSync();
		    
            /*kafkaConsumer.commitAsync(new OffsetCommitCallback() {
				
				@Override
				public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
					if(exception!=null) {
						System.out.println("报错");
					}else {
						try {
							System.out.println("等待producer");
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});*/
        }
        Long endtime = System.currentTimeMillis();
	    System.out.println("时间："+ String.valueOf(endtime - starttime));
	}
	
	//指定topic的某个分区消费
	public void consumer_kafka_partition(Properties properties){
		
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
		try {
			//指定从clmtopic的第0个partition开始消费
			TopicPartition tp = new TopicPartition("clmtopic", 1);
			//对consumer指定partition分配
			kafkaConsumer.assign(Arrays.asList(tp));
			//调用consumer.seekToBeginning指定从   头   开始消费
			kafkaConsumer.seekToBeginning(Arrays.asList(tp));
			
			//调用consumer.seekToBeginning指定从   最后   开始消费
			//kafkaConsumer.seekToEnd(Arrays.asList(tp));
			
			while (true) {
			    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
			    for (ConsumerRecord<String, String> record : records) {
			        //System.out.printf("offset = %d, value = %s", record.offset(), record.value());
			        System.out.println("offset = "+record.offset() +"_"+ "value = "+ record.value());
			    }
			    kafkaConsumer.commitSync();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			kafkaConsumer.close();
		}
	}
	
	
	//指定topic的某个offset消费
	public void consumer_kafka_offset(Properties properties){
		
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
		try {
			//指定从clmtopic的第0个partition开始消费
			TopicPartition tp = new TopicPartition("clmtopic", 1);
			//对consumer指定partition分配
			kafkaConsumer.assign(Arrays.asList(tp));
			//指定offset
			kafkaConsumer.seek(tp, 968);
			
			while (true) {
			    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
			    for (ConsumerRecord<String, String> record : records) {
			        //System.out.printf("offset = %d, value = %s", record.offset(), record.value());
			        System.out.println("offset = "+record.offset() +"_"+ "value = "+ record.value());
			    }
			    kafkaConsumer.commitSync();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			kafkaConsumer.close();
		}
	}

}

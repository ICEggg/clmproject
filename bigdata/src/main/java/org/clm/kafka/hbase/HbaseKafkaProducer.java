package org.clm.kafka.hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class HbaseKafkaProducer {
	private static Properties properties = new Properties();
	private static Producer<String, String> producer = null;
	
	public HbaseKafkaProducer(){
		properties.put("bootstrap.servers", "10.71.4.55:9092,10.71.4.56:9092");
		//properties.put("bootstrap.servers", "172.168.0.63:9092,172.168.0.64:9092,172.168.0.65:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(properties);
	}
	
	public static void main(String[] args) {
		HbaseKafkaProducer testkafka = new HbaseKafkaProducer();
		
		File file = new File("C:\\Users\\dev\\Desktop\\20M测试数据\\20M\\20180315\\catalog_returns_20180315.dat");
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String message = "";
			while(bufferedReader.readLine()!=null) {
				message = bufferedReader.readLine();
				//生产者
				testkafka.producer_kafka(HbaseKafkaProducer.properties,message);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			producer.close();
		}
	}
	
	public void producer_kafka(Properties properties,String message){
        try {
             producer.send(new ProducerRecord<String, String>("clmtopic", message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}

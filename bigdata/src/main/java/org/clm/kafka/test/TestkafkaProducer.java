package org.clm.kafka.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class TestkafkaProducer {
	private static Properties properties = new Properties();
	private static Producer<String, String> producer = null;
	
	public TestkafkaProducer(){
		properties.put("bootstrap.servers", "10.71.4.54:9092,10.71.4.56:9092");
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
		TestkafkaProducer testkafka = new TestkafkaProducer();
		
		File file = new File("C:\\Users\\dev\\Desktop\\20M测试数据\\20M\\20180315\\catalog_returns_20180315.dat");
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String message = "";
			while(bufferedReader.readLine()!=null) {
				message = bufferedReader.readLine();
				//生产者
				testkafka.producer_kafka(TestkafkaProducer.properties,message);
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
	
	
	//测试数据
	/*public void producer_kafka(Properties properties,String message){
        try {
            for (int i = 0; i < 10; i++) {
                String msg = "New Message " + i;
                producer.send(new ProducerRecord<String, String>("clmtopic", msg));
                System.out.println("Sent:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            producer.close();
        }

    }*/
		
	//读取文件数据写入kafka
	public void producer_kafka(Properties properties,String message){
        try {
             producer.send(new ProducerRecord<String, String>("clmtopic", message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
}


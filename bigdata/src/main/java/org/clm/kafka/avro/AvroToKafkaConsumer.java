package org.clm.kafka.avro;

import java.util.Collections;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;

/**
 * 使用 Twitter 的 Bijection 类库实现 avro 的序列化与反序列化       消费
 * @author lenovo
 *
 */
public class AvroToKafkaConsumer {
	public static void main(String[] args) {
		/*String schemaStr =  "{" +
			            "   \"type\" : \"record\"," +
			            "   \"name\" : \"test\"," +
			            "   \"fields\" : ["
			            + "{ \"name\" : \"id\", \"type\" : \"int\" },"
			            + "{ \"name\" : \"name\", \"type\" : \"string\"},"
			            + "{ \"name\" : \"age\", \"type\" : \"int\" }]" +
			            "}";*/
		
		String schemaStr = "{"
						+ "\"type\":\"record\","
						+ "\"name\":\"User\","
						+ "\"namespace\":\"avro\","
						+ "\"fields\":["
						+ "{\"name\":\"line\",\"type\":[\"string\",\"null\"]}]"
						+ "}";

		
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.71.4.55:9092,10.71.4.55:9092,10.71.4.55:9092");
        props.put("group.id", "clmgroup1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);

        consumer.subscribe(Collections.singletonList("clmavrotopic"));
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(schemaStr);
        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);
        
        try {
            while(true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(1000);
                for (ConsumerRecord<String, byte[]> record : records) {
                    GenericRecord genericRecord = recordInjection.invert(record.value()).get();
                    /*System.out.println("value = [user.id = " + genericRecord.get("id") + ", " +
                            "user.name = " + genericRecord.get("name") + ", " +
                            "user.age = " + genericRecord.get("age") + "], " + 
                            "partition = " + record.partition() + ", " + 
                            "offset = " + record.offset());*/
                    System.out.println(genericRecord.get("line"));
                }
            }
        } finally {
            consumer.close();
        }
    }
	
}

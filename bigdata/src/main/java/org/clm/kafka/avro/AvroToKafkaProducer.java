package org.clm.kafka.avro;

import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;

/**
 * 使用 Twitter 的 Bijection 类库实现 avro 的序列化与反序列化        生产
 * @author lenovo
 *
 */
public class AvroToKafkaProducer {
	public static void main(String[] args) {
		String schemaStr =  "{" +
			            "   \"type\" : \"record\"," +
			            "   \"name\" : \"test\"," +
			            "   \"fields\" : ["
			            + "{ \"name\" : \"id\", \"type\" : \"int\" },"
			            + "{ \"name\" : \"name\", \"type\" : \"string\"},"
			            + "{ \"name\" : \"age\", \"type\" : \"int\" }]" +
			            "}";
	        
	        Properties props = new Properties();
	        props.put("bootstrap.servers", "172.168.0.63:9092,172.168.0.64:9092,172.168.0.65:9092");
	        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

	        Schema.Parser parser = new Schema.Parser();
	        Schema schema = parser.parse(schemaStr);
	        Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);
	        
	        Producer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
	        
	        for (int i = 0; i < 10; i++) {
	            GenericData.Record avroRecord = new GenericData.Record(schema);
	            avroRecord.put("id", i);
	            avroRecord.put("name", "name" + i);
	            avroRecord.put("age", 22);
	            byte[] avroRecordBytes = recordInjection.apply(avroRecord);
	            ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>("clmtopic_in", avroRecordBytes);
	            producer.send(record);
	        }
	        producer.close();
	}

}

package org.clm.demo.kafka;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableKafka//配合@Configuration注解一起用，会激活Kafka的基于注解驱动的消息消费功能
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    @Value("${spring.producer.retries}")
    private int retries;
    @Value("${spring.producer.batch-size}")
    private int batchSize;
    @Value("${spring.producer.buffer-memory}")
    private int bufferMemory;


    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap();
        props.put("bootstrap.servers", this.servers);
        props.put("retries", this.retries);
        props.put("batch.size", this.batchSize);
        props.put("buffer.memory", this.bufferMemory);
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);
        return props;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory(this.producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate kafkaTemplate = new KafkaTemplate(this.producerFactory());
        kafkaTemplate.send("test", "连接到Kafka。。。。。。。");
        return kafkaTemplate;
    }

}

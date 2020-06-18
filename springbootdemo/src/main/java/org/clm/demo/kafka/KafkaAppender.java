package org.clm.demo.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.ConsoleAppender;
import lombok.Data;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Properties;


@Data
public class KafkaAppender extends AppenderBase<ILoggingEvent> {
    //private KafkaTemplate<String, String> kafkaTemplate;

    private Producer producer;

    private String bootstrapServers;
    @Override
    public void start() {
        super.start();
//            Properties props = new Properties();
//            props.put("bootstrap.servers", bootstrapServers);
//            producer = new KafkaProducer(props);

        }


    @Override
    protected void append(ILoggingEvent eventObject) {
//        String msg = eventObject.getFormattedMessage();
//        ProducerRecord record = new ProducerRecord("topic",msg);
//        producer.send(record);
    }

}

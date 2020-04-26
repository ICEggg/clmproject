package org.clm.demo.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import lombok.Data;
import org.springframework.kafka.core.KafkaTemplate;


@Data
public class KafkaAppender extends ConsoleAppender<ILoggingEvent> {
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void start() {
        super.start();
    }


    @Override
    protected void append(ILoggingEvent eventObject) {
        if (kafkaTemplate != null){
            kafkaTemplate.send("test", eventObject.getFormattedMessage());
        }

        /*else if (SpringContextHolder.applicationContext != null){
            // 配置加载完毕发送消息到Kafka SpringContextHolder获取spring上下文（这个代码就不贴了）
            kafkaTemplate = (KafkaTemplate<String, String>)SpringContextHolder.getBean("kafkaTemplate");
            kafkaTemplate.send("test", eventObject.getFormattedMessage());
        }*/



    }

}

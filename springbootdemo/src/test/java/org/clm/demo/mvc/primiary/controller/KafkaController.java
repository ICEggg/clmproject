package org.clm.demo.mvc.primiary.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KafkaController.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class KafkaController {

    @Test
    public void sendLogToKafka() {
        for (int i = 0; i < 10; i++) {
            log.info("this is a test msg from kafka client " + i);
        }
    }
}

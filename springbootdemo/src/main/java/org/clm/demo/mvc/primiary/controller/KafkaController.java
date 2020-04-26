package org.clm.demo.mvc.primiary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "kafkaCon")
public class KafkaController {
    @Autowired
    private KafkaTemplate template;


}

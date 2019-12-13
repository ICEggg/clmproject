package org.clm.kafka.kafka;


public class KafkaConsumerProducerDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
        producerThread.start();
        KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);
        consumerThread.start();
	}

}

package com.i2i.intern.pixcell;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerService {

    // Kafka settings
    private static final String KAFKA_BOOTSTRAP_SERVERS = "34.41.186.2:9092";
    private static final String KAFKA_GROUP_ID = "usage";
    private static final String KAFKA_TOPIC = "usage";

    private final KafkaRecordProcessor recordProcessor;

    public KafkaConsumerService() {
        this.recordProcessor = new KafkaRecordProcessor();
    }

    public void startConsuming() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("usage"));

        while (true) {
            consumer.poll(java.time.Duration.ofMillis(100)).forEach(recordProcessor::processRecord);
        }
    }
}
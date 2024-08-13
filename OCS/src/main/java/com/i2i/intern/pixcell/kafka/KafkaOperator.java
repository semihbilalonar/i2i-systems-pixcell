package com.i2i.intern.pixcell.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaOperator {

    private final KafkaProducer<String, String> producer;
    private final String topic = "chf"; //Kafka topic'i

    public KafkaOperator() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "34.41.186.2:9092"); // Kafka broker adresi
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
    }

    public void sendKafkaMessage(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        try {
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get(); // Mesaj kontrol
            System.out.printf("Sent message to topic %s partition %d with offset %d%n",
                    metadata.topic(), metadata.partition(), metadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //wallet mesajları
    public void sendKafkaWalletMessage(long msisdn, int userId, int amount) {
        String message = String.format("{\"type\": \"wallet\", \"msisdn\": \"%d\", \"userId\": %d, \"amount\": %d}", msisdn, userId, amount);
        sendKafkaMessage(message);
    }

    //usage mesajları
    public void sendKafkaVoiceUsageMessage(String type, long msisdn, int userId, int amount, int bal_lvl_minutes,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d, \"amount_minutes\": %d, \"bal_lvl_minutes\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}", type, msisdn, userId, amount, bal_lvl_minutes,name,surname,email);
        sendKafkaMessage(message);
    }
    public void sendKafkaSmsUsageMessage(String type, long msisdn, int userId, int amount, int bal_lvl_sms,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d, \"amount_sms\": %d, \"bal_lvl_sms\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}", type, msisdn, userId, amount, bal_lvl_sms,name,surname,email);
        sendKafkaMessage(message);
    }
    public void sendKafkaDataUsageMessage(String type, long msisdn, int userId, int amount, int bal_lvl_data,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d, \"amount_data\": %d, \"bal_lvl_data\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}", type, msisdn, userId, amount, bal_lvl_data,name,surname,email);
        sendKafkaMessage(message);
    }

    //threshold mesajları
    public void sendKafkaUsageThresholdMessage(String type, long msisdn, int userId) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d,}", type, msisdn, userId);
        sendKafkaMessage(message);
    }
}

package org.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaOperator {

    private final KafkaProducer<String, String> producer;
    private final String topic = "CGF"; //Kafka topic'i

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
    public void sendKafkaUsageMessage(String type, long msisdn, int userId, int amount) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d, \"amount\": %d}", type, msisdn, userId, amount);
        sendKafkaMessage(message);
    }
}

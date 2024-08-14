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
    //Kafka topic'i
    private final String topicCHF = "CHF";
    private final String topicBalance = "balanceTopic";
    private final String topicUsage = "usage";
    private final String topicNotification= "notification";

    public KafkaOperator() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "34.41.186.2:9092"); // Kafka broker adresi
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
    }

    public void sendKafkaMessage(String topic,String message) {
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
        sendKafkaMessage(topicCHF,message);
    }

    //usage mesajları
    public void sendKafkaVoiceUsageMessage(String type, long msisdn, int userId, int amount, int bal_lvl_minutes,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d, \"amount_minutes\": %d, \"bal_lvl_minutes\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}", type, msisdn, userId, amount, bal_lvl_minutes,name,surname,email);
        sendKafkaMessage(topicCHF, message);
    }
    public void sendKafkaSmsUsageMessage(String type, long msisdn, int userId, int amount, int bal_lvl_sms,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d, \"amount_sms\": %d, \"bal_lvl_sms\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}", type, msisdn, userId, amount, bal_lvl_sms,name,surname,email);
        sendKafkaMessage(topicCHF,message);
    }
    public void sendKafkaDataUsageMessage(String type, long msisdn, int userId, int amount, int bal_lvl_data,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d, \"amount_data\": %d, \"bal_lvl_data\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}", type, msisdn, userId, amount, bal_lvl_data,name,surname,email);
        sendKafkaMessage(topicCHF,message);
    }

    //threshold mesajları
    public void sendKafkaUsageThresholdMessage(String type, long msisdn, int userId) {
        String message = String.format("{\"type\": \"%s\", \"msisdn\": \"%d\", \"userId\": %d,}", type, msisdn, userId);
        sendKafkaMessage(topicCHF,message);
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //usage topic date ekle
    public void sendKafkaUsageDataMessage(String type,long msisdn, int bal_lvl_data, int amount,String date) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_data\": %d, \"amount_data\": %d, \"date\": %s}",type, msisdn, bal_lvl_data,amount,date);
        sendKafkaMessage(topicUsage,message);
    }
    public void sendKafkaUsageVoiceMessage(String type,long msisdn, int bal_lvl_minutes, int amount) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_minutes\": %d, \"amount_minutes\": %d}",type, msisdn, bal_lvl_minutes,amount);
        sendKafkaMessage(topicUsage,message);
    }
    public void sendKafkaUsageSmsMessage(String type,long msisdn, int bal_lvl_sms, int amount) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_sms\": %d, \"amount_sms\": %d}",type, msisdn, bal_lvl_sms,amount);
        sendKafkaMessage(topicUsage,message);
    }

    //balance topic
    public void sendKafkaBalanceDataMessage(String type,long msisdn, int bal_lvl_data, int amount,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_data\": %d, \"amount_data\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}", type,msisdn, bal_lvl_data,amount,name,surname,email);
        sendKafkaMessage(topicBalance,message);
    }
    public void sendKafkaBalanceVoiceMessage(String type,long msisdn, int bal_lvl_minutes, int amount,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_minutes\": %d, \"amount_minutes\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}",type, msisdn, bal_lvl_minutes,amount,name,surname,email);
        sendKafkaMessage(topicBalance,message);
    }
    public void sendKafkaBalanceSmsMessage(String type,long msisdn, int bal_lvl_sms, int amount,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_sms\": %d, \"amount_sms\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}",type, msisdn, bal_lvl_sms,amount,name,surname,email);
        sendKafkaMessage(topicBalance,message);
    }

    //Notification topic
    public void sendKafkaNotificationDataMessage(String type,long msisdn, int bal_lvl_data, int amount,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_data\": %d, \"amount_data\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}",type, msisdn, bal_lvl_data,amount,name,surname,email);
        sendKafkaMessage(topicNotification,message);
    }
    public void sendKafkaNotificationVoiceMessage(String type,long msisdn, int bal_lvl_minutes, int amount,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_minutes\": %d, \"amount_minutes\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}",type, msisdn, bal_lvl_minutes,amount,name,surname,email);
        sendKafkaMessage(topicNotification,message);
    }
    public void sendKafkaNotificationSmsMessage(String type,long msisdn, int bal_lvl_sms, int amount,String name,String surname,String email) {
        String message = String.format("{\"type\": \"%s\",\"msisdn\": \"%d\", \"bal_lvl_sms\": %d, \"amount_sms\": %d, \"name\": %s, \"surname\": %s, \"email\": %s}",type, msisdn, bal_lvl_sms,amount,name,surname,email);
        sendKafkaMessage(topicNotification,message);
    }

}

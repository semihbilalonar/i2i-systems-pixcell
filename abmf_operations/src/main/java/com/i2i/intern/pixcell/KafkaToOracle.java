
package com.i2i.intern.pixcell;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;

public class KafkaToOracle {

    private static Connection getConnection() throws SQLException {
        // Oracle veritabanı bağlantı bilgilerini buraya ekleyin
        String url = "jdbc:oracle:thin:@34.123.74.34:1521:xe";
        String user = "c##emre";
        String password = "coltu";
        return DriverManager.getConnection(url, user, password);
    }

    private static void updateBalanceTable(long msisdn, int remain, String type) {
        String sql = "";

        // Hangi türde güncelleme yapılacağını belirleyin
        switch (type.toLowerCase()) {
            case "sms":
                sql = "UPDATE balance SET bal_lvl_sms = ? WHERE cust_id = (SELECT cust_id FROM customer WHERE msisdn = ?)";
                break;
            case "data":
                sql = "UPDATE balance SET bal_lvl_data = ? WHERE cust_id = (SELECT cust_id FROM customer WHERE msisdn = ?)";
                break;
            case "voice":
                sql = "UPDATE balance SET bal_lvl_minutes = ? WHERE cust_id = (SELECT cust_id FROM customer WHERE msisdn = ?)";
                break;
            case "wallet":
                sql = "UPDATE balance SET bal_lvl_money = ? WHERE cust_id = (SELECT cust_id FROM customer WHERE msisdn = ?)";
                break;
            default:
                System.out.println("Unsupported type: " + type);
                return;
        }

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Parametreleri ayarla
            statement.setInt(1, remain);
            statement.setLong(2, msisdn);

            // Sorguyu çalıştır
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Balance table updated successfully for MSISDN: " + msisdn);
            } else {
                System.out.println("No matching record found for MSISDN: " + msisdn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Hata işleme
        }
    }

    private static void processRecord(ConsumerRecord<String, String> record) {
        String jsonString = record.value();
        System.out.println("Received JSON: " + jsonString);

        try {
            JSONObject json = new JSONObject(jsonString);

            if (json.has("type") && json.has("msisdn") && json.has("remain")) {
                String type = json.getString("type");
                // MSISDN'yi long türüne dönüştür
                long msisdn = json.getLong("msisdn");
                int remain = json.getInt("remain");

                // Türüne göre güncelleme yap
                updateBalanceTable(msisdn, remain, type);

            } else {
                System.out.println("JSON missing one of the required keys.");
            }
        } catch (Exception e) {
            System.out.println("Error processing JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Kafka tüketici ayarları
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "34.41.186.2:9092"); // Kafka sunucusu
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "balance-update-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Kafka tüketici oluştur
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Topic adını buraya yazın
        consumer.subscribe(Collections.singletonList("balanceTopic")); // Topic adı

        // Mesajları işle
        while (true) {
            consumer.poll(java.time.Duration.ofMillis(100)).forEach(record -> processRecord(record));
        }
    }
}


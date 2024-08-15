package com.i2i.intern.pixcell;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.Instant;

public class KafkaRecordProcessor {

    private final UserUsageDetailDAO userUsageDetailDAO;

    public KafkaRecordProcessor() {
        this.userUsageDetailDAO = new UserUsageDetailDAO();
    }

    public void processRecord(ConsumerRecord<String, String> record) {
        String jsonString = record.value();
        System.out.println("Received JSON: " + jsonString);

        try {
            JSONObject json = new JSONObject(jsonString);

            if (json.has("type") && json.has("msisdn") && json.has("remain") && json.has("amount")) {
                String type = json.getString("type");
                long msisdn = json.getLong("msisdn");
                int remain = json.getInt("remain");
                int amount = json.getInt("amount");

                Timestamp usageDate = Timestamp.from(Instant.now());

                userUsageDetailDAO.insertUserUsageDetail(msisdn, type, remain, amount, usageDate);

            } else {
                System.err.println("JSON is missing one of the required keys.");
            }
        } catch (Exception e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }
    }
}
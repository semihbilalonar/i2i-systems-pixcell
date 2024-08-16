package com.i2i.intern.pixcell.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.UsageRecordMessage;
import org.apache.kafka.common.serialization.Deserializer;

public class UsageRecordMessageDeserializer implements Deserializer<UsageRecordMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UsageRecordMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, UsageRecordMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing UsageRecordMessage", e);
        }
    }
}

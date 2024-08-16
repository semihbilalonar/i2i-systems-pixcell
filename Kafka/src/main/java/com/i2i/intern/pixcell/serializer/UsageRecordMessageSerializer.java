package com.i2i.intern.pixcell.serializer;

import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.UsageRecordMessage;

public class UsageRecordMessageSerializer implements Serializer<UsageRecordMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UsageRecordMessageSerializer() {
    }

    @Override
    public byte[] serialize(String topic, UsageRecordMessage data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing message", e);
        }
    }
}

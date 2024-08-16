package com.i2i.intern.pixcell.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.BalanceMessage;
import org.apache.kafka.common.serialization.Serializer;

public class BalanceMessageSerializer implements Serializer<BalanceMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, BalanceMessage data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing BalanceMessage", e);
        }
    }
}

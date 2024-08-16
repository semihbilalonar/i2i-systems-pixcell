package com.i2i.intern.pixcell.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.BalanceMessage;
import org.apache.kafka.common.serialization.Deserializer;

public class BalanceMessageDeserializer implements Deserializer<BalanceMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BalanceMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, BalanceMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing BalanceMessage", e);
        }
    }
}

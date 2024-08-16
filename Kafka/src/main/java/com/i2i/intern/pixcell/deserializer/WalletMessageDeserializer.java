package com.i2i.intern.pixcell.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.WalletMessage;
import org.apache.kafka.common.serialization.Deserializer;

public class WalletMessageDeserializer implements Deserializer<WalletMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public WalletMessage deserialize(String topic, byte[] data) {
        try {

            return objectMapper.readValue(data, WalletMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing WalletMessage", e);
        }
    }
}


package com.i2i.intern.pixcell.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.NotificationMessage;
import com.i2i.intern.pixcell.message.WalletMessage;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.WalletMessage;
import org.apache.kafka.common.serialization.Serializer;


public class WalletMessageSerializer implements Serializer<WalletMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, WalletMessage data) {
        try {

            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing WalletMessage", e);
        }
    }
}

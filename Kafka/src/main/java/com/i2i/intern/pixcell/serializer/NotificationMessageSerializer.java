package com.i2i.intern.pixcell.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.NotificationMessage;
import org.apache.kafka.common.serialization.Serializer;

public class NotificationMessageSerializer implements Serializer<NotificationMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, NotificationMessage data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing NotificationMessage", e);
        }
    }
}
package com.i2i.intern.pixcell.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2i.intern.pixcell.message.NotificationMessage;
import org.apache.kafka.common.serialization.Deserializer;

public class NotificationMessageDeserializer implements Deserializer<NotificationMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NotificationMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, NotificationMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing NotificationMessage", e);
        }
    }
}

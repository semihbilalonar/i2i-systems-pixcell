package org.example.requestMessage;


import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AkkaRequestMessage createMessageFromJSON(String jsonString) throws Exception {

        return objectMapper.readValue(jsonString, AkkaRequestMessage.class);

    }
}
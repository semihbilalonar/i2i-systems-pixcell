package com.i2i.intern.pixcell;

public class Main {
    public static void main(String[] args) {
        KafkaConsumerService consumerService = new KafkaConsumerService();
        consumerService.startConsuming();

    }
}
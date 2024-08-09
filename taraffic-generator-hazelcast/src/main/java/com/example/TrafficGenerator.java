package com.example;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;

public class TrafficGenerator {
    public static void main(String[] args) {
        Config config = Config.load("hazelcast.xml");

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        FlakeIdGenerator idGenerator = hazelcastInstance.getFlakeIdGenerator("traffic-generator");

        for (int i = 0; i < 10000; i++) {
            long id = idGenerator.newId();
            System.out.println("Traffic event ID: " + id);
        }

        hazelcastInstance.shutdown();
    }
}
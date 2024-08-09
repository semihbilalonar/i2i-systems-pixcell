// Main.java
package com.example;

import com.hazelcast.config.Config;
import com.hazelcast.config.FlakeIdGeneratorConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();

        FlakeIdGeneratorConfig flakeConfig = new FlakeIdGeneratorConfig("my-flake-id-generator");
        flakeConfig.setBitsNodeId(1); 
        flakeConfig.setPrefetchCount(10);
        flakeConfig.setPrefetchValidityMillis(10000); //10 saniye

        config.addFlakeIdGeneratorConfig(flakeConfig);

        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);

        FlakeIdGenerator flakeIdGenerator = hazelcast.getFlakeIdGenerator("my-flake-id-generator");

        // fake id üretiyor burada
        for (int i = 0; i < 10; i++) {
            long id = flakeIdGenerator.newId();
            System.out.println("Generated ID: " + id);
        }

        hazelcast.shutdown();
    }
}
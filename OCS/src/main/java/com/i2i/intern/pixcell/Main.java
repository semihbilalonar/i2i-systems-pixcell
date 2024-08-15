package com.i2i.intern.pixcell;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.i2i.intern.pixcell.akka.AkkaListener;

import java.io.File;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Config config = ConfigFactory.parseFile(new File("akka-listener-config.conf"));
        final ActorSystem system = ActorSystem.create("MySystem", config);

        final ActorRef listener = system.actorOf(AkkaListener.props(), "listener");

        final ActorRef akkaListener = system.actorOf(AkkaListener.props(), "akkaListener");

        akkaListener.tell("{\"type\": \"voice\", \"senderMSISDN\": \"12345678901\", \"Location\": 49, \"usageAmount\": 5  }", ActorRef.noSender());
        akkaListener.tell("{\"type\": \"data\", \"senderMSISDN\": \"12345678901\",\"Location\": 49, \"ratingNumber\": 0, \"usageAmount\": 5  }", ActorRef.noSender());
        akkaListener.tell("{\"type\": \"sms\", \"senderMSISDN\": \"12345678901\", \"Location\": 49, \"usageAmount\": 5  }", ActorRef.noSender());


    }
}
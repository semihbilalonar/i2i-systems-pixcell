package com.i2i.intern.pixcell.TGF;

import com.i2i.intern.pixcell.TGF.actors.AkkaActor;
import com.i2i.intern.pixcell.TGF.actors.DeadLetterListener;
import com.i2i.intern.pixcell.TGF.command.CommandHandler;
import com.i2i.intern.pixcell.TGF.constants.TransType;
import com.i2i.intern.pixcell.TGF.management.DelayManager;
import com.i2i.intern.pixcell.TGF.management.StatsManager;
import com.i2i.intern.pixcell.TGF.management.ThreadsManager;
import com.i2i.intern.pixcell.TGF.trafficGenerators.TrafficGenerator;
import com.i2i.intern.pixcell.TGF.util.Clock;
import com.i2i.intern.pixcell.VoltDbWrapper;
import com.typesafe.config.ConfigFactory;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;

public class Main {

    public static void main(String[] args) {

        //data managers
        StatsManager statsManager = new StatsManager();


        DelayManager delayManager = new DelayManager(3000, 3000, 3000); //initial delay 1s, 3tps

        //start akka system and actors
        ActorSystem actorSystem = ActorSystem.create("system", ConfigFactory.load("application.conf"));
        ActorRef actor = actorSystem.actorOf(Props.create(AkkaActor.class), "TGFActor");
        ActorRef deadLetterListener = actorSystem.actorOf(Props.create(DeadLetterListener.class, statsManager), "deadLetterListener");
        //subscribe deadLetterListener actor to deadLetters
        actorSystem.eventStream().subscribe(deadLetterListener, DeadLetter.class);

        //runnable traffic generators
        TrafficGenerator voice = new TrafficGenerator(TransType.VOICE, actor, statsManager, delayManager);
        TrafficGenerator data = new TrafficGenerator(TransType.DATA, actor, statsManager, delayManager);
        TrafficGenerator sms = new TrafficGenerator(TransType.SMS, actor, statsManager, delayManager);

        //manages starting and stopping of threads
        ThreadsManager threadsManager = new ThreadsManager(voice, data, sms);

        //run the main loop
        CommandHandler commander = new CommandHandler(threadsManager, statsManager, delayManager, actor);
        commander.startCommander();

        //exit the application
        Clock.delay(1);
        actorSystem.terminate();
        Clock.delay(1000);
        System.exit(0);

    }
}
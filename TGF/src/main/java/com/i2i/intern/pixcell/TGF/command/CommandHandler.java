package com.i2i.intern.pixcell.TGF.command;

import akka.actor.ActorRef;
import com.i2i.intern.pixcell.TGF.constants.TransType;
import com.i2i.intern.pixcell.TGF.management.DelayManager;
import com.i2i.intern.pixcell.TGF.management.StatsManager;
import com.i2i.intern.pixcell.TGF.management.ThreadsManager;
import com.i2i.intern.pixcell.TGF.util.RandomGenerator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandHandler {

    private static final Scanner sc = new Scanner(System.in);

    private final ThreadsManager threadManager;
    private final StatsManager statsManager;
    private final DelayManager delayManager;
    private final ActorRef actor;

    public CommandHandler(ThreadsManager threadManager, StatsManager statsManager, DelayManager delayManager, ActorRef actor) {
        this.threadManager = threadManager;
        this.statsManager = statsManager;
        this.delayManager = delayManager;
        this.actor = actor;
    }

    public void startCommander() {

        outer:
        while(true) {

            System.out.println("enter command: ");
            String input = sc.nextLine();

            switch (input) {
                case "start"         -> threadManager.startThreads();
                case "stop"          -> threadManager.stopThreads();
                case "terminate"     -> { break outer; }
                case "setDelay"      -> updateDelayAll();
                case "setDelayVoice" -> updateDelay(TransType.VOICE);
                case "setDelayData"  -> updateDelay(TransType.DATA);
                case "setDelaySms"   -> updateDelay(TransType.SMS);
                case "setTps"        -> setDelayByTps();
                case "printDelay"    -> delayManager.printDelay();
                case "printStats"    -> statsManager.printStats();
                case "resetStats"    -> statsManager.resetStats();
                case "updateMsisdn"  -> RandomGenerator.fetchMsisdn();  //update the list of msisdn from Hazelcast
                case "testRandom"    -> printTransTest();               //print a random transaction to test values
                case "s" -> sendTransTest();
                default -> System.out.println("unrecognized command...");
            }
        }

        threadManager.stopThreads();
        sc.close();
    }

    private void sendTransTest(){
        String trans = "{\"type\": \"data\", \"senderMSISDN\": \"11111111111\",\"Location\": 49, \"ratingNumber\": 0, \"usageAmount\": 5  }";

        actor.tell(trans, ActorRef.noSender());
    }

    private void setDelayByTps() {

        try {
            System.out.println("enter tps value:");

            float tpmPerGenerator = sc.nextFloat() / 3;

            int delay = Math.round(1000 / tpmPerGenerator);

            delayManager.setDelayAll(delay);

        } catch (InputMismatchException e) {
            System.out.println("unsupported input format...");
        }

    }

    private void updateDelay(TransType type) {

        try {
            System.out.println("enter delay value:");
            int delay = sc.nextInt();

            switch (type) {
                case DATA -> delayManager.setDataDelay(delay);
                case VOICE -> delayManager.setVoiceDelay(delay);
                case SMS -> delayManager.setSmsDelay(delay);
            }

        } catch (InputMismatchException e) {
            System.out.println("unsupported input format...");
        }
    }

    private void updateDelayAll() {

        try {
            System.out.println("enter delay value:");
            int delay = sc.nextInt();
            delayManager.setDelayAll(delay);

        } catch (InputMismatchException e) {
            System.out.println("unsupported input format...");
        }
    }

    private void printTransTest() {
        System.out.println(".");
    }

}

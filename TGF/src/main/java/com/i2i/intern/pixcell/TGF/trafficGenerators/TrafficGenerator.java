package com.i2i.intern.pixcell.TGF.trafficGenerators;

import akka.actor.ActorRef;
import com.i2i.intern.pixcell.TGF.constants.TransType;
import com.i2i.intern.pixcell.TGF.management.DelayManager;
import com.i2i.intern.pixcell.TGF.management.StatsManager;
import com.i2i.intern.pixcell.TGF.util.Clock;
import com.i2i.intern.pixcell.TGF.util.RandomGenerator;

public class TrafficGenerator implements Runnable{
    private final TransType type;
    private final ActorRef actor;
    private boolean isGenerate = true;

    private final StatsManager statsManager;
    private final DelayManager delayManager;

    /**
     * @param type type of transaction to be generated
     * @param actor akka actor which will send transactions
     * @param delayManager manages the delay time between transactions
     * @param statsManager manages stats of generator, counts transactions and dropped transactions
     */
    public TrafficGenerator(TransType type, ActorRef actor, StatsManager statsManager, DelayManager delayManager) {

        this.type = type;
        this.actor = actor;
        this.statsManager = statsManager;
        this.delayManager = delayManager;
    }

    @Override
    public void run() {

        statsManager.resetTimer(type);
        isGenerate = true;

        while(isGenerate) {

            sendTransaction();
            statsManager.incrementCounter(type);
            Clock.delay(delayManager.getDelay(type));
        }

        System.out.println(type + " traffic generation stopped...");
    }

    private void sendTransaction() {

        long msisdn = RandomGenerator.randomMsisdn();
        int location = RandomGenerator.randomLocation();
        int amount = RandomGenerator.randomDataUsage();

        System.out.println(type + " sending transaction " + msisdn + " to " + amount);
        switch (type) {
            case DATA -> {
                String trans ="{\"type\": \"data\", \"senderMSISDN\": \""+ msisdn +"\",\"Location\":, "+ location +", \"usageAmount\": "+ RandomGenerator.randomDataUsage()+"  }";
                actor.tell(trans, ActorRef.noSender());
            }
            case VOICE -> {
                String trans =  "{\"type\": \"voice\", \"senderMSISDN\": \""+ msisdn +"\", \"Location\": "+ location + ", \"usageAmount\": "+ amount+"  }";
                actor.tell(trans, ActorRef.noSender());
            }
            case SMS -> {
                String trans = "{\"type\": \"sms\", \"senderMSISDN\": \""+ msisdn +"\", \"Location\": "+ location +", \"usageAmount\": "+ amount+" }";
                actor.tell(trans, ActorRef.noSender());
            }
        }//end switch
    }

    //set isGenerate bool to false to break the generation loop
    public void stop() {
        this.isGenerate = false;
    }


}

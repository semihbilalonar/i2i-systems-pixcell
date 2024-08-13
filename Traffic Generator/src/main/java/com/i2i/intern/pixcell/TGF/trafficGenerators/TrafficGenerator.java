package com.i2i.intern.pixcell.TGF.trafficGenerators;

import akka.actor.ActorRef;

import com.i2i.DataTransaction;
import com.i2i.SmsTransaction;
import com.i2i.VoiceTransaction;
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
     *
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

        String msisdn = RandomGenerator.randomMsisdn();
        int location = RandomGenerator.randomLocation();

        switch (type) {
            case DATA -> {
                DataTransaction trans = new DataTransaction(
                        msisdn,
                        location,
                        RandomGenerator.randomDataUsage(),
                        RandomGenerator.randomRatingGroup());
                actor.tell(trans, ActorRef.noSender());
            }
            case VOICE -> {
                VoiceTransaction trans =  new VoiceTransaction(
                        msisdn,
                        RandomGenerator.randomMsisdn(),
                        location,
                        RandomGenerator.randomDuration()
                );
                actor.tell(trans, ActorRef.noSender());
            }
            case SMS -> {
                SmsTransaction trans = new SmsTransaction(
                        msisdn,
                        RandomGenerator.randomMsisdn(),
                        location
                );
                actor.tell(trans, ActorRef.noSender());
            }
        }//end switch
    }

    public void stop() {
        this.isGenerate = false;
    }


}

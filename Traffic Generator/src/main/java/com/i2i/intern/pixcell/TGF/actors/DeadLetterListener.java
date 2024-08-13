package com.i2i.intern.pixcell.TGF.actors;

import com.i2i.DataTransaction;
import com.i2i.SmsTransaction;
import com.i2i.VoiceTransaction;
import com.i2i.intern.pixcell.TGF.constants.TransType;
import com.i2i.intern.pixcell.TGF.management.StatsManager;

import akka.actor.AbstractActor;
import akka.actor.DeadLetter;

public class DeadLetterListener extends AbstractActor {
    com.i2i.intern.pixcell.TGF.management.StatsManager statsManager;

    public DeadLetterListener(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DeadLetter.class, deadLetter -> {

                    if(deadLetter.message() instanceof DataTransaction)
                        ((StatsManager) statsManager).incrementDeadCounter(TransType.DATA);
                    else if (deadLetter.message() instanceof VoiceTransaction)
                        statsManager.incrementDeadCounter(TransType.VOICE);
                    else if (deadLetter.message() instanceof SmsTransaction)
                        statsManager.incrementDeadCounter(TransType.SMS);

                }
                )
                .build();
    }
}
package com.i2i.intern.pixcell.TGF.actors;

import akka.actor.AbstractActor;
import akka.actor.DeadLetter;
import com.i2i.intern.pixcell.TGF.constants.TransType;
import com.i2i.intern.pixcell.TGF.management.StatsManager;

import javax.print.DocFlavor;

public class DeadLetterListener extends AbstractActor {
    StatsManager statsManager;

    public DeadLetterListener(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(DeadLetter.class, deadLetter -> {

                    if(deadLetter.message() instanceof String)
                        statsManager.incrementDeadCounter(TransType.DATA);
                    if(deadLetter.message() instanceof String)
                        statsManager.incrementDeadCounter(TransType.SMS);
                    if(deadLetter.message() instanceof String)
                        statsManager.incrementDeadCounter(TransType.VOICE);


                }).build();
    }
}

package com.i2i.intern.pixcell.TGF.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import com.typesafe.config.Config;

import javax.print.DocFlavor;

public class AkkaActor extends AbstractActor {

    Config config = getContext().getSystem().settings().config();
    ActorSelection remoteActor = getContext().getSystem().actorSelection(config.getString("CHF.path"));

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, trans -> remoteActor.tell(trans, self()))
                .matchAny(o -> System.out.println("received unknown message"))
                .build();
    }

}

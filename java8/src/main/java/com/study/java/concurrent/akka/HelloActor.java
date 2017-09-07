package com.study.java.concurrent.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;

/**
 * @author Jeffrey
 * @since 2017/08/30 15:59
 */
public class HelloActor extends AbstractActor{


    public static Props props() {
        return Props.create(HelloActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(String.class, s -> getSender().tell(null, getSelf()))
            .build();
    }
}

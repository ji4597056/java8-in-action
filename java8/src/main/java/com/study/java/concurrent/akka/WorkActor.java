package com.study.java.concurrent.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @author Jeffrey
 * @since 2017/08/30 15:27
 */
public class WorkActor extends AbstractActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public static Props props() {
        return Props.create(WorkActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, s -> {
            Thread.sleep(1000);
            getSender().tell(s, getSelf());
        }).build();
    }
}

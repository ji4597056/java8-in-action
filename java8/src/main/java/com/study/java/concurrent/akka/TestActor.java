package com.study.java.concurrent.akka;

import akka.actor.AbstractActor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jeffrey
 * @since 2017/08/30 14:04
 */
public class TestActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(o -> {
            TimeUnit.SECONDS.sleep(1L);
            System.out.println("received" + o.toString() + ",thread id:" + Thread.currentThread().getId());
        }).build();
    }

}

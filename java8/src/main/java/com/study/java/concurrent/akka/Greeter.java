package com.study.java.concurrent.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.study.java.concurrent.akka.Printer.Greeting;
import java.time.LocalTime;

/**
 * @author Jeffrey
 * @since 2017/09/06 10:41
 */
public class Greeter extends AbstractActor {

    static public Props props(String message, ActorRef printerActor) {
        return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    }

    static public class WhoToGreet {

        public final String who;

        public WhoToGreet(String who) {
            this.who = who;
        }

        public String getWho() {
            return who;
        }
    }

    static public class Greet {

        public Greet() {
        }
    }

    private final String message;
    private final ActorRef printerActor;
    private String greeting = "";

    public Greeter(String message, ActorRef printerActor) {
        this.message = message;
        this.printerActor = printerActor;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(WhoToGreet.class, wtg -> {
                this.greeting = message + ", " + wtg.who;
                getSender().tell(new WhoToGreet(wtg.getWho() + LocalTime.now()), getSelf());
            })
            .match(Greet.class, x -> {
                printerActor.tell(new Greeting(greeting), getSelf());
            })
            .build();
    }
}

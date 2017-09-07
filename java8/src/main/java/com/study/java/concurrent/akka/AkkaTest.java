package com.study.java.concurrent.akka;

import static org.junit.Assert.assertEquals;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.study.java.concurrent.akka.Greeter.Greet;
import com.study.java.concurrent.akka.Greeter.WhoToGreet;
import com.study.java.concurrent.akka.Printer.Greeting;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;

/**
 * @author Jeffrey
 * @since 2017/09/06 10:30
 */
public class AkkaTest {

    public static ActorSystem system;

    public static TestKit testProbe;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
        testProbe = new TestKit(system);
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));
        helloGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
        helloGreeter.tell(new Greet(), ActorRef.noSender());
//        WhoToGreet whoToGreet = testProbe.expectMsgClass(WhoToGreet.class);
//        assertEquals("Akka", whoToGreet.getWho());
//        Greet greet = testProbe.expectMsgClass(Greet.class);
//        assertNotNull(greet);
        Greeting greeting = testProbe.expectMsgClass(Greeting.class);
        assertEquals("Hello, Akka", greeting.message);
    }

    @Test
    public void testScheduler() throws IOException {
        final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));
        WhoToGreet whoToGreet = new WhoToGreet("Akka");
        // 定时任务
        system.scheduler()
            .schedule(Duration.Zero(), Duration.create(1, TimeUnit.SECONDS), helloGreeter,
                whoToGreet, system.dispatcher(), testProbe.getRef());
        while (true) {
            WhoToGreet who = testProbe.expectMsgClass(WhoToGreet.class);
            System.out.println(who.getWho());
        }
    }

}

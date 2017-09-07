package com.study.java.concurrent.akka;

import static org.junit.Assert.assertEquals;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.TestKit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Awaitable;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * @author Jeffrey
 * @since 2017/08/30 13:49
 */
public class ActorTest {

    public static final ActorSystem system = ActorSystem.create("device-system");

    @Test
    public void testReplyWithEmptyReadingIfNoTemperatureIsKnown() {
        TestKit probe = new TestKit(system);
        ActorRef deviceActor = system.actorOf(Device.props("group", "device"));
        deviceActor.tell(new Device.ReadTemperature(42L), probe.testActor());
        Device.RespondTemperature response = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(42L, response.requestId);
        assertEquals(Optional.empty(), response.value);
    }

    @Test
    public void testReplyWithLatestTemperatureReading() {
        TestKit probe = new TestKit(system);
        ActorRef deviceActor = system.actorOf(Device.props("group", "device"));

        deviceActor.tell(new Device.RecordTemperature(1L, 24.0), probe.testActor());
        assertEquals(1L, probe.expectMsgClass(Device.TemperatureRecorded.class).requestId);

        deviceActor.tell(new Device.ReadTemperature(2L), probe.testActor());
        Device.RespondTemperature response1 = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(2L, response1.requestId);
        assertEquals(Optional.of(24.0), response1.value);

        deviceActor.tell(new Device.RecordTemperature(3L, 55.0), probe.testActor());
        assertEquals(3L, probe.expectMsgClass(Device.TemperatureRecorded.class).requestId);

        deviceActor.tell(new Device.ReadTemperature(4L), probe.testActor());
        Device.RespondTemperature response2 = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(4L, response2.requestId);
        assertEquals(Optional.of(55.0), response2.value);
    }

    @Test
    public void testTestActor() throws Exception {
        ActorSystem system = ActorSystem.create("test");
        final ActorRef actorRef = system.actorOf(Props.create(TestActor.class));
        long start = System.currentTimeMillis();
        IntStream.range(0, 10000)
            .forEach(i -> system.actorOf(Props.create(TestActor.class)).tell("hi" + i, actorRef));
        System.out.println(">>> Press ENTER to exit <<<");
        try {
            System.in.read();
        } finally {
            system.terminate();
            System.out.println("cost time:" + (System.currentTimeMillis() - start));
        }
    }

    @Test
    public void testWorkActor() throws Exception {
        Future<Object> future1 = Patterns.ask(system.actorOf(WorkActor.props()), "hah", 1000);
        Future<Object> future2 = Patterns.ask(system.actorOf(WorkActor.props()), "hi", 1000);
        String result = Await.result(future1, Duration.create(3, TimeUnit.SECONDS)) + (String) Await
            .result(future2, Duration.create(3, TimeUnit.SECONDS));
        System.out.println("result:" + result);
    }

}

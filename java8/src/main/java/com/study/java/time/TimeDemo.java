package com.study.java.time;

import static java.time.temporal.ChronoField.NANO_OF_SECOND;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Jeffrey
 * @since 2017/03/22 16:38
 */
public class TimeDemo {

    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test
    public void test() throws InterruptedException {
        Instant instant = Instant.now();
        Thread.sleep(1500L);
        Instant end = Instant.now();
        Duration duration = Duration.between(instant, end);
        System.out.println(duration.getSeconds());
        System.out.println(duration.toNanos());
        Instant now = Instant.now();
        System.out.println(now.getLong(NANO_OF_SECOND));
        System.out.println(now.getEpochSecond());
        System.out.println(System.currentTimeMillis());
        ex.expect(UnsupportedTemporalTypeException.class);
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm::ss").format(end));
    }

    @Test
    public void test1() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
        LocalDate data1 = LocalDate.of(2015, 12, 12);
        System.out.println(data1.toString());
        System.out.println(localDate.minusDays(2).getDayOfMonth());
        LocalDate firstTuesday = LocalDate.of(2016, 1, 1)
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        System.out.println(firstTuesday.toString());
        System.out.println(LocalDate.now().withDayOfMonth(1).toString());
        System.out.println(LocalDate.now().minusDays(15).toString());
        System.out.println(LocalDate.parse("2012-12-30").toString());
    }

    @Test
    public void test2() {
        System.out.println(LocalDateTime.now().toString());
        System.out.println(LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        System.out.println(LocalDateTime.now().atZone(ZoneId.of("America/Los_Angeles")).toString());
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).toString());
        System.out
            .println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).toString());
    }

    @Test
    public void test3() {
        LocalDateTime t1 = LocalDateTime.of(LocalDate.of(2018, 4, 17), LocalTime.now());
        LocalDateTime t2 = LocalDateTime.of(LocalDate.of(2018, 4, 18), LocalTime.now());
        LocalDateTime t3 = LocalDateTime.of(LocalDate.of(2018, 4, 19), LocalTime.now());
        Duration duration = Duration.between(t1, t2);
        Period period = Period.ofDays(1);
        TemporalAdjusters.previous(DayOfWeek.SATURDAY);
    }
}

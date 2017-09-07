package com.study.java.lambda.date;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/05/18 9:28
 */
public class LocalDateDemo {

    @Test
    public void test1() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.get(ChronoField.DAY_OF_MONTH));
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toString());
        Instant instant = Instant.ofEpochSecond(3);
        instant = Instant.now();
        System.out.println(instant.getEpochSecond());// 时间戳
    }

    @Test
    public void test2() throws InterruptedException {
        Instant instant1 = Instant.now();
        Thread.sleep(500L);
        Instant instant2 = Instant.now();
        // Duration　时间级别（秒，纳秒）
        Duration duration = Duration.between(instant1, instant2);
        System.out.println(duration.getNano() / 1_000_000);
    }

    @Test
    public void test3() {
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = localDate1.plusDays(120);
        // Period 日期级别(年,月,日)
        Period period = Period.between(localDate1, localDate2);
        System.out.println(period.getMonths());
    }

    @Test
    public void test4() {
        LocalDate date1 = LocalDate.of(2017, 5, 18);
        LocalDate date2 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date2);
    }

    @Test
    public void test5() {
        LocalDate date = LocalDate.now();
        TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
            temporal -> {
                DayOfWeek dow =
                    DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                int dayToAdd = 1;
                if (dow == DayOfWeek.FRIDAY) {
                    dayToAdd = 3;
                }
                if (dow == DayOfWeek.SATURDAY) {
                    dayToAdd = 2;
                }
                return temporal.plus(dayToAdd, ChronoUnit.DAYS);
            });
        System.out.println(date.with(nextWorkingDay));
    }

    @Test
    public void test6() {
        LocalDate date1 = LocalDate.parse("20140318",
            DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(date1);
        LocalDate date2 = LocalDate.parse(date1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(date2);
    }

    @Test
    public void test7() {
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        System.out.println(zoneId.getId());
    }

    @Test
    public void test8() {
        Date date = new Date();
        System.out.println(date);
        LocalDateTime localDateTime = LocalDateTime.now();
        date = Date.from(localDateTime.minusDays(15).atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date);
    }
}

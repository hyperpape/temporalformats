package com.justinblank;

import org.openjdk.jmh.annotations.*;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class DateFormatBenchmarkRolling {

    private Date date = new Date();

    private ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Setup(Level.Invocation)
    public void setup() {
        zdt = zdt.plusNanos(111111111111229L);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public ZonedDateTime doNothing() {
        return zdt;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public String testInstantFormat() {
        return dtf.format(zdt);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public String testMyFormat() {
        StringBuilder sb = new StringBuilder(29);
        sb.append(zdt.get(ChronoField.YEAR))
                .append('-');
        long month = zdt.get(ChronoField.MONTH_OF_YEAR);
        if (month < 10) {
            sb.append('0');
        }
        sb.append(month)
                .append('-');
        long dayOfMonth = zdt.get(ChronoField.DAY_OF_MONTH);
        if (dayOfMonth < 10) {
            sb.append('0');
        }
        sb.append(dayOfMonth)
                .append('T');
        long hourOfDay = zdt.get(ChronoField.HOUR_OF_DAY);
        if (hourOfDay < 10) {
            sb.append('0');
        }
        sb.append(hourOfDay)
                .append(':');
        long minute = zdt.get(ChronoField.MINUTE_OF_HOUR);
        if (minute < 10) {
            sb.append('0');
        }
        sb.append(minute)
                .append(':');
        long seconds = zdt.get(ChronoField.SECOND_OF_MINUTE);
        if (seconds < 10) {
            sb.append('0');
        }
        sb.append(seconds)
                .append('.');
        long millis = zdt.get(ChronoField.MILLI_OF_SECOND);
        if (millis < 100) {
            sb.append('0');
            if (millis < 10) {
                sb.append('0');
            }
        }
        sb.append(millis);
        sb.append(zdt.getOffset());
        return sb.toString();
    }
}


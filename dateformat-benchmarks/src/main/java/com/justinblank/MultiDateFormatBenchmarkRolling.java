package com.justinblank;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class MultiDateFormatBenchmarkRolling {

    private Date date = new Date();

    private ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    DateTimeFormatter dtf2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    DateTimeFormatter dtf3 = DateTimeFormatter.ISO_LOCAL_DATE;
    DateTimeFormatter dtf4 = DateTimeFormatter.ISO_LOCAL_TIME;
    List<DateTimeFormatter> formatters = new ArrayList<>();

    public MultiDateFormatBenchmarkRolling() {
        formatters.add(dtf);
        formatters.add(dtf2);
        formatters.add(dtf3);
        formatters.add(dtf4);
    }

    @Setup(Level.Invocation)
    public void setup() {
        zdt = zdt.plusNanos(111111111111229L);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void doNothing(Blackhole bh) {
        bh.consume(zdt);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testDateTimeFormatters(Blackhole bh) {
        for (var formatter : formatters) {
            bh.consume(formatter.format(zdt));
        }
    }

}


package com.justinblank;

import com.justinblank.temporalformatter.StandardFormats;
import com.justinblank.temporalformatter.TemporalFormatter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class MultiTargetDateFormatBenchmarkRolling {

    private Date date = new Date();

    private ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    private LocalDateTime ldt = LocalDateTime.from(zdt);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    DateTimeFormatter dtf2 = DateTimeFormatter.ISO_LOCAL_DATE;
    DateTimeFormatter dtf3 = DateTimeFormatter.ISO_LOCAL_TIME;

    List<DateTimeFormatter> formatters = new ArrayList<>();
    List<TemporalFormatter> temporalFormatters = new ArrayList<>();

    public MultiTargetDateFormatBenchmarkRolling() {
        formatters.add(dtf);
        formatters.add(dtf2);
        formatters.add(dtf3);

        temporalFormatters.add(StandardFormats.ISO_LOCAL_DATE_TIME);
        temporalFormatters.add(StandardFormats.ISO_LOCAL_DATE);
        temporalFormatters.add(StandardFormats.ISO_LOCAL_TIME);
    }

    @Setup(Level.Invocation)
    public void setup() {
        zdt = zdt.plusNanos(111111111111229L);
        ldt = ldt.plusNanos(111111111111229L);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void doNothing(Blackhole bh) {
        bh.consume(zdt);
        bh.consume(ldt);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testDateTimeFormatters(Blackhole bh) {
        for (var formatter : formatters) {
            bh.consume(formatter.format(zdt));
            bh.consume(formatter.format(ldt));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testTemporalFormatters(Blackhole bh) {
        for (var formatter : temporalFormatters) {
            bh.consume(formatter.format(zdt));
            bh.consume(formatter.format(ldt));
        }
    }

}


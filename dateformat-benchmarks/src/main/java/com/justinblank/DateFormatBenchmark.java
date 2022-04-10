package com.justinblank;

import com.justinblank.dateformats.DateFormatCreator;
import com.justinblank.dateformats.TemporalFormatter;
import org.openjdk.jmh.annotations.*;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class DateFormatBenchmark {

    private Date date = new Date();

    private ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

    // TODO
    // The behavior of SSS is different from the DateTimeFormatter, figure out how to fix
    // SimpleDateFormat isoOffsetDateTimeSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    // SimpleDateFormat isoLocalDateTimeSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    DateTimeFormatter isoOffsetDateTimeDTF = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    DateTimeFormatter isoLocalDateTimeDTF = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    TemporalFormatter isoLocalDateTimeTemporalFormatter = new DateFormatCreator().generateFormatter(List.of("yyyy", "-", "MM", "-", "dd", "T", "HH", ":", "mm", ":", "ss", ".", "SSS"));

    @Setup
    public void setup() {
    }

//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.NANOSECONDS)
//    public String testISOOffsetDateTimeSimpleDateFormat() {
//        return isoOffsetDateTimeSDF.format(date);
//    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public String testISOOffsetDateTimeDateTimeFormat() {
        return isoOffsetDateTimeDTF.format(zdt);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public String testISOOffsetDateTimeFormatHandWritten() {
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

//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.NANOSECONDS)
//    public String testISOLocalDateTimeSimpleDateFormat() {
//        return isoLocalDateTimeSDF.format(date);
//    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public String testISOLocalDateTimeDateTimeFormat() {
        return isoLocalDateTimeDTF.format(zdt);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public String testISOLocalDateTimeTemporalFormatter() {
        return isoLocalDateTimeTemporalFormatter.format(zdt);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public String testISOLocalDateTimeFormatHandWritten() {
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
        return sb.toString();
    }

}

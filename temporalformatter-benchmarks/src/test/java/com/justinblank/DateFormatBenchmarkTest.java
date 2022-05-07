package com.justinblank;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateFormatBenchmarkTest {

    @Test
    public void testFormats() {
        var bench = new DateFormatBenchmark();
        // local dates
        assertEquals(bench.testISOLocalDateDateTimeFormatter(), bench.testISOLocalDateHandwritten());
        assertEquals(bench.testISOLocalDateDateTimeFormatter(), bench.testISOLocalDateTemporalFormatter());

        // local datetimes
        assertEquals(bench.testISOLocalDateTimeDateTimeFormatter(), bench.testISOLocalDateTimeFormatHandWritten());
        assertEquals(bench.testISOLocalDateTimeDateTimeFormatter(), bench.testISOLocalDateTimeTemporalFormatter());
    }

    @Test
    public void testFormatsWithMillisMultiplesOfTen() {
        var ldt = LocalDateTime.of(2000, 1, 1, 1, 1, 1, 450 * 1000 * 1000);
        var date = Date.from(ZonedDateTime.of(ldt, ZoneId.systemDefault()).toInstant());
        var bench = new DateFormatBenchmark(date);
        // local dates
        assertEquals(bench.testISOLocalDateDateTimeFormatter(), bench.testISOLocalDateHandwritten());
        assertEquals(bench.testISOLocalDateDateTimeFormatter(), bench.testISOLocalDateTemporalFormatter());

        // local datetimes
        assertEquals(bench.testISOLocalDateTimeDateTimeFormatter(), bench.testISOLocalDateTimeFormatHandWritten());
        assertEquals(bench.testISOLocalDateTimeDateTimeFormatter(), bench.testISOLocalDateTimeTemporalFormatter());
    }
}
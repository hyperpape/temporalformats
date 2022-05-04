package com.justinblank;

import org.junit.jupiter.api.Test;

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
}
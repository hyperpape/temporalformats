package com.justinblank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateFormatBenchmarkTest {

    @Test
    public void testFormats() {
        var bench = new DateFormatBenchmark();
        assertEquals(bench.testISOOffsetDateTimeDateTimeFormat(), bench.testISOOffsetDateTimeFormatHandWritten());

        assertEquals(bench.testISOLocalDateTimeDateTimeFormat(), bench.testISOLocalDateTimeFormatHandWritten());
        assertEquals(bench.testISOLocalDateTimeDateTimeFormat(), bench.testISOLocalDateTimeTemporalFormatter());
    }
}
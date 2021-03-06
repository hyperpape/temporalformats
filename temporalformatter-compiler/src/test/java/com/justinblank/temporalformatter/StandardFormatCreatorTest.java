package com.justinblank.temporalformatter;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.time.api.constraints.DateTimeRange;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.justinblank.temporalformatter.StandardFormatCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardFormatCreatorTest {

    public static final TemporalFormatter ISO_LOCAL_DATE_TIME = isoLocalDateTime();

    public static final TemporalFormatter ISO_LOCAL_DATE = isoLocalDate();

    public static final TemporalFormatter ISO_LOCAL_TIME = isoLocalTime();


    // For reasons I have yet to dig into, these tests give less coverage than I'd hope
    @Property
    void generativeLocalDateTimeFormattingTest(@ForAll @DateTimeRange(min = "0001-01-01T01:32:21", max = "2020-12-31T03:11:11") ZonedDateTime zdt) {
        assertEquals(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(zdt), ISO_LOCAL_DATE_TIME.format(zdt));
    }

    @Property
    void generativeLocalDateFormattingTest(@ForAll @DateTimeRange(min = "0001-01-01T01:32:21", max = "2020-12-31T03:11:11") ZonedDateTime zdt) {
        assertEquals(DateTimeFormatter.ISO_LOCAL_DATE.format(zdt), ISO_LOCAL_DATE.format(zdt));
    }

    @Property
    void generativeLocalTimeFormattingTest(@ForAll @DateTimeRange(min = "0001-01-01T01:32:21", max = "2020-12-31T03:11:11") ZonedDateTime zdt) {
        assertEquals(DateTimeFormatter.ISO_LOCAL_TIME.format(zdt), ISO_LOCAL_TIME.format(zdt));
    }

    @Test
    void localTimeFormattingTest() {
        var zdt = ZonedDateTime.now();
        assertEquals(DateTimeFormatter.ISO_LOCAL_TIME.format(zdt), ISO_LOCAL_TIME.format(zdt));
    }

    @Test
    void localTimeFormattingSpecifiedTime() {
        var ldt = LocalDateTime.of(1990, 1, 1, 1, 1, 1, 124119);
        assertEquals(DateTimeFormatter.ISO_LOCAL_TIME.format(ldt), ISO_LOCAL_TIME.format(ldt));
        var zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        assertEquals(DateTimeFormatter.ISO_LOCAL_TIME.format(zdt), ISO_LOCAL_TIME.format(zdt));
    }

    @Test
    void largeLocalTimeFormattingTest() {
        var ldt = LocalDateTime.of(10001, 1, 1, 1, 1, 1);
        assertEquals(DateTimeFormatter.ISO_LOCAL_TIME.format(ldt), ISO_LOCAL_TIME.format(ldt));

        ldt = LocalDateTime.of(100001, 1, 1, 1, 1, 1);
        assertEquals(DateTimeFormatter.ISO_LOCAL_TIME.format(ldt), ISO_LOCAL_TIME.format(ldt));
    }

    @Test
    void testMillisEndingInZero() {
        var ldt = LocalDateTime.of(2000, 1, 1, 1, 1, 1, 450 * 1000 * 1000);
        assertEquals(DateTimeFormatter.ISO_LOCAL_TIME.format(ldt), ISO_LOCAL_TIME.format(ldt));
    }
}

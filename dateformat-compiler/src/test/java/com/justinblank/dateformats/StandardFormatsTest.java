package com.justinblank.dateformats;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.time.api.constraints.DateTimeRange;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardFormatsTest {

    @Property
    void generativeLocalDateTimeFormattingTest(@ForAll @DateTimeRange(min = "2019-01-01T01:32:21", max = "2020-12-31T03:11:11") ZonedDateTime zdt) {
        assertEquals(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(zdt), StandardFormats.ISO_LOCAL_DATE_TIME.format(zdt));
    }

    @Property
    void generativeLocalDateFormattingTest(@ForAll @DateTimeRange(min = "2019-01-01T01:32:21", max = "2020-12-31T03:11:11") ZonedDateTime zdt) {
        assertEquals(DateTimeFormatter.ISO_LOCAL_DATE.format(zdt), StandardFormats.ISO_LOCAL_DATE.format(zdt));
    }

    @Property
    void generativeLocalTimeFormattingTest(@ForAll @DateTimeRange(min = "2019-01-01T01:32:21", max = "2020-12-31T03:11:11") ZonedDateTime zdt) {
        assertEquals(DateTimeFormatter.ISO_LOCAL_TIME.format(zdt), StandardFormats.ISO_LOCAL_TIME.format(zdt));
    }
}

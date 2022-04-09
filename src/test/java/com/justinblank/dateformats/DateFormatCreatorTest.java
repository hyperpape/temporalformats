package com.justinblank.dateformats;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateFormatCreatorTest {

    @Test
    public void testCreationEmptyFormat() throws Exception {
        new DateFormatCreator().generateFormatter(List.of());
    }

    @Test
    public void testCreationOneString() throws Exception {
        new DateFormatCreator().generateFormatter(List.of("YYYY"));
    }

    @Test
    public void testCreationTwoStrings() throws Exception {
        new DateFormatCreator().generateFormatter(List.of("SSS", "SSS"));
    }

    @Test
    public void testCanFormatYear() throws Exception {
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        assertEquals("2022", new DateFormatCreator().generateFormatter(List.of("yyyy")).format(time));
    }

    @Test
    public void testCanFormatManyThings() throws Exception {
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 1, ZoneId.systemDefault());
        assertEquals("2022-1-2", new DateFormatCreator().generateFormatter(List.of("yyyy", "-", "MM", "-", "dd", "-", "HH", ":", "mm", ":", "ss")).format(time));
    }
}

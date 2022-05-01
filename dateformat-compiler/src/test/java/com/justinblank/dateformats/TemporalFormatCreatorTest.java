package com.justinblank.dateformats;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemporalFormatCreatorTest {

    @Test
    public void testCreationEmptyFormat() throws Exception {
        new TemporalFormatCreator().generateFormatter("");
    }

    @Test
    public void testCreationYear() throws Exception {
        new TemporalFormatCreator().generateFormatter("yyyy");
    }

    @Test
    public void testCreationOffset() throws Exception {
        new TemporalFormatCreator().generateFormatter("XXX");
    }

    @Test
    public void testCreationTwoStrings() throws Exception {
        new TemporalFormatCreator().generateFormatter("SSSSSS");
    }

    @Test
    public void testCreationFormatSpecifierWithChronoField() throws Exception {
        TemporalFormatter temporalFormatter = new TemporalFormatCreator().generateFormatter(List.of(FormatSpecifier.fieldSpecifier(ChronoField.NANO_OF_SECOND, 0, 9, '.')));
        temporalFormatter.format(Instant.now());
    }

    @Test
    public void testCanFormatMonth() throws Exception {
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 666, ZoneId.systemDefault());
        assertEquals("01", new TemporalFormatCreator().generateFormatter("MM").format(time));
    }

    @Test
    public void testCanFormatYear() throws Exception {
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        assertEquals("2022", new TemporalFormatCreator().generateFormatter("yyyy").format(time));
    }

    @Test
    public void testCanFormatManyThings() throws Exception {
        List<String> strings = Arrays.asList("yyyy", "-", "MM", "-", "dd", " ", "HH", ":", "mm", ":", "ss");
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 666, ZoneId.systemDefault());
        assertEquals("2022-01-02 15:04:05", new TemporalFormatCreator().generateFormatter("yyyy-MM-dd HH:mm:ss").format(time));
    }

}

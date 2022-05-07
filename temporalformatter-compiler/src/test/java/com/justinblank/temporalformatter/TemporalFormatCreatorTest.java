package com.justinblank.temporalformatter;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

import static com.justinblank.temporalformatter.TemporalFormatCreator.generateFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemporalFormatCreatorTest {

    @Test
    public void testCreationEmptyFormat() throws Exception {
        generateFormatter("");
    }

    @Test
    public void testCreationYear() throws Exception {
        generateFormatter("yyyy");
    }

    @Test
    public void testCreationOffset() throws Exception {
        generateFormatter("XXX");
    }

    @Test
    public void testCreationTwoStrings() throws Exception {
        generateFormatter("SSSSSS");
    }

    @Test
    public void testCreationFormatSpecifierWithChronoField() throws Exception {
        TemporalFormatter temporalFormatter = generateFormatter(List.of(FormatSpecifier.fieldSpecifier(ChronoField.NANO_OF_SECOND, 0, 9, '.')));
        temporalFormatter.format(Instant.now());
    }

    @Test
    public void testCanFormatMonth() throws Exception {
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 666, ZoneId.systemDefault());
        assertEquals("01", generateFormatter("MM").format(time));
    }

    @Test
    public void testCanFormatYear() throws Exception {
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        assertEquals("2022", generateFormatter("yyyy").format(time));
    }

    @Test
    public void testCanFormatManyThings() throws Exception {
        List<String> strings = Arrays.asList("yyyy", "-", "MM", "-", "dd", " ", "HH", ":", "mm", ":", "ss");
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 666, ZoneId.systemDefault());
        assertEquals("2022-01-02 15:04:05", generateFormatter("yyyy-MM-dd HH:mm:ss").format(time));
    }
}

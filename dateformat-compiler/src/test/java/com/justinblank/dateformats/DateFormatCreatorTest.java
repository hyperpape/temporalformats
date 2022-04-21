package com.justinblank.dateformats;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.time.api.constraints.DateTimeRange;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateFormatCreatorTest {

    @Test
    public void testCreationEmptyFormat() throws Exception {
        new DateFormatCreator().generateFormatter(new ArrayList<>());
    }

    @Test
    public void testCreationYearWeek() throws Exception {
        List<String> strings = Arrays.asList("YYYY");
        new DateFormatCreator().generateFormatter(strings);
    }

    @Test
    public void testCreationYear() throws Exception {
        List<String> strings = Arrays.asList("yyyy");
        new DateFormatCreator().generateFormatter(strings);
    }

    @Test
    public void testCreationOffset() throws Exception {
        List<String> strings = Arrays.asList("XXX");
        new DateFormatCreator().generateFormatter(strings);
    }

    @Test
    public void testCreationTwoStrings() throws Exception {
        List<String> strings = Arrays.asList("SSS", "SSS");
        new DateFormatCreator().generateFormatter(strings);
    }

    @Test
    public void testCanFormatMonth() throws Exception {
        List<String> strings = Arrays.asList("MM");
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 666, ZoneId.systemDefault());
        assertEquals("01", new DateFormatCreator().generateFormatter(strings).format(time));
    }

    @Test
    public void testCanFormatYear() throws Exception {
        List<String> strings = Arrays.asList("yyyy");
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        assertEquals("2022", new DateFormatCreator().generateFormatter(strings).format(time));
    }

    @Test
    public void testCanFormatManyThings() throws Exception {
        List<String> strings = Arrays.asList("yyyy", "-", "MM", "-", "dd", " ", "HH", ":", "mm", ":", "ss");
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 666, ZoneId.systemDefault());
        assertEquals("2022-01-02 15:04:05", new DateFormatCreator().generateFormatter(strings).format(time));
    }


}

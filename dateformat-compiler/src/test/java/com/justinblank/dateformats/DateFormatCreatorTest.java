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
        new DateFormatCreator().generateFormatter("");
    }

    @Test
    public void testCreationYear() throws Exception {
        new DateFormatCreator().generateFormatter("yyyy");
    }

    @Test
    public void testCreationOffset() throws Exception {
        new DateFormatCreator().generateFormatter("XXX");
    }

    @Test
    public void testCreationTwoStrings() throws Exception {
        new DateFormatCreator().generateFormatter("SSSSSS");
    }

    @Test
    public void testCanFormatMonth() throws Exception {
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 666, ZoneId.systemDefault());
        assertEquals("01", new DateFormatCreator().generateFormatter("MM").format(time));
    }

    @Test
    public void testCanFormatYear() throws Exception {
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        assertEquals("2022", new DateFormatCreator().generateFormatter("yyyy").format(time));
    }

    @Test
    public void testCanFormatManyThings() throws Exception {
        List<String> strings = Arrays.asList("yyyy", "-", "MM", "-", "dd", " ", "HH", ":", "mm", ":", "ss");
        ZonedDateTime time = ZonedDateTime.of(2022, 1, 2, 15, 4, 5, 666, ZoneId.systemDefault());
        assertEquals("2022-01-02 15:04:05", new DateFormatCreator().generateFormatter("yyyy-MM-dd HH:mm:ss").format(time));
    }

}

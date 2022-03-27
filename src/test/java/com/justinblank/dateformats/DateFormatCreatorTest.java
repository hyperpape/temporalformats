package com.justinblank.dateformats;

import org.junit.jupiter.api.Test;

import java.util.List;

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
        new DateFormatCreator().generateFormatter(List.of("YYYY", "YYYY"));
    }
}

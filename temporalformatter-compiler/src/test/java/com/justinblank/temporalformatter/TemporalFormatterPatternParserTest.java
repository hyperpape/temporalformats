package com.justinblank.temporalformatter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.justinblank.temporalformatter.TemporalFormatterPatternParser.splitToComponents;
import static org.junit.jupiter.api.Assertions.*;

class TemporalFormatterPatternParserTest {

    @Test
    void testSplitToComponents() throws Exception {
        try {
            assertEquals(Arrays.asList("yyyy", "-", "MM", "-", "dd", "T", "HH", ":", "mm", ":", "ss", ".", "SSS", "XXX"),
                    splitToComponents("yyyy-MM-ddTHH:mm:ss.SSSXXX"));
            assertEquals(Arrays.asList("yyyy", "-", "MM", "-", "dd", "T", "HH", ":", "mm", ":", "ss", ".", "SSS"),
                    splitToComponents("yyyy-MM-ddTHH:mm:ss.SSS"));
            assertEquals(Arrays.asList("yyyy", "-", "MM", "-", "dd"), splitToComponents("yyyy-MM-dd"));
            assertEquals(Arrays.asList("HH", ":", "mm", ":", "ss"), splitToComponents("HH:mm:ss"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

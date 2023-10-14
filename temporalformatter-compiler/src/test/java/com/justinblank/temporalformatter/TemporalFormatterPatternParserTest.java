package com.justinblank.temporalformatter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.justinblank.temporalformatter.TemporalFormatterPatternParser.splitToComponents;
import static org.junit.jupiter.api.Assertions.*;

class TemporalFormatterPatternParserTest {

    @Test
    void testSplitToComponents() throws Exception {
        assertEquals(Arrays.asList("yyyy", "-", "MM", "-", "dd", "T", "HH", ":", "mm", ":", "ss", ".", "SSS", "XXX"),
                splitToComponents("yyyy-MM-ddTHH:mm:ss.SSSXXX").stream().map(FormatSpecifier::toString).collect(Collectors.toList()));
        assertEquals(Arrays.asList("yyyy", "-", "MM", "-", "dd", "T", "HH", ":", "mm", ":", "ss", ".", "SSS"),
                splitToComponents("yyyy-MM-ddTHH:mm:ss.SSS").stream().map(FormatSpecifier::toString).collect(Collectors.toList()));
        assertEquals(Arrays.asList("yyyy", "-", "MM", "-", "dd"), splitToComponents("yyyy-MM-dd").stream().map(FormatSpecifier::toString).collect(Collectors.toList()));
        assertEquals(Arrays.asList("HH", ":", "mm", ":", "ss"), splitToComponents("HH:mm:ss").stream().map(FormatSpecifier::toString).collect(Collectors.toList()));
    }
}

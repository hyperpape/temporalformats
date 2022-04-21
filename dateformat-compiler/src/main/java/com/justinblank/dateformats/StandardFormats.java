package com.justinblank.dateformats;

import java.util.Arrays;
import java.util.List;

public class StandardFormats {

    public static final TemporalFormatter ISO_LOCAL_DATE_TIME = isoLocalDateTime();

    public static final TemporalFormatter ISO_LOCAL_DATE = isoLocalDate();

    public static final TemporalFormatter ISO_LOCAL_TIME = isoLocalTime();


    static TemporalFormatter isoOffsetDateTime() {
        List<String> strings = Arrays.asList("yyyy", "-", "MM", "-", "dd", "T", "HH", ":", "mm", ":", "ss", ".", "SSS", "XXX");
        return new DateFormatCreator().generateFormatter(strings);
    }

    static TemporalFormatter isoLocalDateTime() {
        List<String> strings = Arrays.asList("yyyy", "-", "MM", "-", "dd", "T", "HH", ":", "mm", ":", "ss", ".", "SSS");
        return new DateFormatCreator().generateFormatter(strings);
    }


    static TemporalFormatter isoLocalDate() {
        List<String> strings = Arrays.asList("yyyy", "-", "MM", "-", "dd");
        return new DateFormatCreator().generateFormatter(strings);
    }

    static TemporalFormatter isoLocalTime() {
        List<String> strings = Arrays.asList("HH", ":", "mm", ":", "ss");
        return new DateFormatCreator().generateFormatter(strings);
    }
}

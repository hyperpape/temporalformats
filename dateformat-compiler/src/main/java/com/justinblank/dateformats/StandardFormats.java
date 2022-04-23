package com.justinblank.dateformats;

import java.text.ParseException;

public class StandardFormats {

    public static final TemporalFormatter ISO_LOCAL_DATE_TIME = isoLocalDateTime();

    public static final TemporalFormatter ISO_LOCAL_DATE = isoLocalDate();

    public static final TemporalFormatter ISO_LOCAL_TIME = isoLocalTime();

    static TemporalFormatter isoOffsetDateTime() {
        try {
            return new DateFormatCreator().generateFormatter("yyyy-MM-ddTHH:mm:ss.SSSXXX");
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static TemporalFormatter isoLocalDateTime() {
        try {
            return new DateFormatCreator().generateFormatter("yyyy-MM-ddTHH:mm:ss.SSS");
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static TemporalFormatter isoLocalDate() {
        try {
            return new DateFormatCreator().generateFormatter("yyyy-MM-dd");
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static TemporalFormatter isoLocalTime() {
        try {
            return new DateFormatCreator().generateFormatter("HH:mm:ss");
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

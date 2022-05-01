package com.justinblank.dateformats;

import java.text.ParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class StandardFormats {

    public static final TemporalFormatter ISO_LOCAL_DATE_TIME = isoLocalDateTime();

    public static final TemporalFormatter ISO_LOCAL_DATE = isoLocalDate();

    public static final TemporalFormatter ISO_LOCAL_TIME = isoLocalTime();

    static TemporalFormatter isoOffsetDateTime() {
        try {
            return new TemporalFormatCreator().generateFormatter("yyyy-MM-ddTHH:mm:ss.SSSXXX", "ISOOffsetDateTime");
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static TemporalFormatter isoLocalDateTime() {
        List<FormatSpecifier> formatSpecifiers = new ArrayList<>();
        formatSpecifiers.add(FormatSpecifier.ofString("yyyy"));
        formatSpecifiers.add(FormatSpecifier.ofString("-"));
        formatSpecifiers.add(FormatSpecifier.ofString("MM"));
        formatSpecifiers.add(FormatSpecifier.ofString("-"));
        formatSpecifiers.add(FormatSpecifier.ofString("dd"));
        formatSpecifiers.add(FormatSpecifier.ofString("T"));
        formatSpecifiers.add(FormatSpecifier.ofString("HH"));
        formatSpecifiers.add(FormatSpecifier.ofString(":"));
        formatSpecifiers.add(FormatSpecifier.ofString("mm"));
        formatSpecifiers.add(FormatSpecifier.ofString(":"));
        formatSpecifiers.add(FormatSpecifier.ofString("ss"));
        formatSpecifiers.add(FormatSpecifier.fieldSpecifier(ChronoField.MILLI_OF_SECOND, 0, 3, '.'));
        return new TemporalFormatCreator().generateFormatter(formatSpecifiers, "ISOLocalDateTime");
    }

    static TemporalFormatter isoLocalDate() {
        try {
            return new TemporalFormatCreator().generateFormatter("yyyy-MM-dd", "ISOLocalDate");
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static TemporalFormatter isoLocalTime() {
        List<FormatSpecifier> formatSpecifiers = new ArrayList<>();
        formatSpecifiers.add(FormatSpecifier.ofString("HH"));
        formatSpecifiers.add(FormatSpecifier.ofString(":"));
        formatSpecifiers.add(FormatSpecifier.ofString("mm"));
        formatSpecifiers.add(FormatSpecifier.ofString(":"));
        formatSpecifiers.add(FormatSpecifier.ofString("ss"));
        formatSpecifiers.add(FormatSpecifier.fieldSpecifier(ChronoField.NANO_OF_SECOND, 0, 9, '.'));
        return new TemporalFormatCreator().generateFormatter(formatSpecifiers, "ISOLocalTime");
    }
}

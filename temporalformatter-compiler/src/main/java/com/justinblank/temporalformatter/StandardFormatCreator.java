package com.justinblank.temporalformatter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import static com.justinblank.temporalformatter.TemporalFormatCreator.*;

public class StandardFormatCreator {

    static TemporalFormatter isoOffsetDateTime() {
        try {
            return generateFormatter(isoOffsetDateTimeFormatSpecifiers(),
                    "com.justinblank.temporalformatter", "ISOOffsetDateTime");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static List<FormatSpecifier> isoOffsetDateTimeFormatSpecifiers() throws ParseException {
        return TemporalFormatterPatternParser.splitToComponents("yyyy-MM-ddTHH:mm:ss.SSSXXX");
    }

    static TemporalFormatter isoLocalDateTime() {
        try {
            List<FormatSpecifier> formatSpecifiers = isoLocalDateTimeFormatSpecifiers();
            return generateFormatter(formatSpecifiers, "com.justinblank.temporalformatter", "ISOLocalDateTime");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static List<FormatSpecifier> isoLocalDateTimeFormatSpecifiers() {
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
        return formatSpecifiers;
    }

    static TemporalFormatter isoLocalDate() {
        try {
            return generateFormatter(isoLocalDateFormatSpecifiers(), "com.justinblank.temporalformatter",
                    "ISOLocalDate");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static List<FormatSpecifier> isoLocalDateFormatSpecifiers() throws ParseException {
        return TemporalFormatterPatternParser.splitToComponents("yyyy-MM-dd");
    }

    static TemporalFormatter isoLocalTime() {
        List<FormatSpecifier> formatSpecifiers = isoLocalTimeFormatSpecifiers();
        return generateFormatter(formatSpecifiers, "com.justinblank.temporalformatter", "ISOLocalTime");
    }

    static List<FormatSpecifier> isoLocalTimeFormatSpecifiers() {
        List<FormatSpecifier> formatSpecifiers = new ArrayList<>();
        formatSpecifiers.add(FormatSpecifier.ofString("HH"));
        formatSpecifiers.add(FormatSpecifier.ofString(":"));
        formatSpecifiers.add(FormatSpecifier.ofString("mm"));
        formatSpecifiers.add(FormatSpecifier.ofString(":"));
        formatSpecifiers.add(FormatSpecifier.ofString("ss"));
        formatSpecifiers.add(FormatSpecifier.fieldSpecifier(ChronoField.NANO_OF_SECOND, 0, 9, '.'));
        return formatSpecifiers;
    }

    public static void main(String[] args) {
        int status = 0;
        try {
            var packageName = "com.justinblank.temporalformatter";
            Files.createDirectory(Path.of("target/standardformatclasses"));
            writeTemporalFormatterClassFile(isoOffsetDateTimeFormatSpecifiers(), packageName, "ISOOffsetDateTime",
                    "target/standardformatclasses/ISOOffsetDateTime.class");
            writeTemporalFormatterClassFile(isoLocalTimeFormatSpecifiers(), packageName,
                    "ISOLocalTime", "target/standardformatclasses/ISOLocalTime.class");
            writeTemporalFormatterClassFile(isoLocalDateFormatSpecifiers(), packageName, "ISOLocalDate",
                    "target/standardformatclasses/ISOLocalDate.class");
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            System.exit(status);
        }
    }
}

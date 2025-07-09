package com.justinblank.temporalformatter;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class TemporalFormatterComponents {

    private static final char[] CHARS = new char[120];

    static {
        for (int i = 0; i < 60; i++) {
            int tens = i / 10;
            int ones = i % 10;
            CHARS[i * 2] = (char)(((int) '0') + tens);
            CHARS[i * 2 + 1] = (char)(((int) '0') + ones);
        }
    }

    public static StringBuilder formatyyyy(TemporalAccessor time, StringBuilder sb) {
        int year = time.get(ChronoField.YEAR) % 10000;
        if (year < 1000) {
            sb.append('0');
            if (year < 100) {
                sb.append('0');
                if (year < 10) {
                    sb.append('0');
                }
            }
        }
        return sb.append(year);
    }

    public static StringBuilder formatMM(TemporalAccessor time, StringBuilder sb) {
        int month = time.get(ChronoField.MONTH_OF_YEAR);
        return appendSmallInt(sb, month);
    }

    public static StringBuilder formatdd(TemporalAccessor time, StringBuilder sb) {
        int day = time.get(ChronoField.DAY_OF_MONTH);
        return appendSmallInt(sb, day);
    }

    private static StringBuilder appendSmallInt(StringBuilder sb, int day) {
        sb.append(CHARS[day * 2]);
        sb.append(CHARS[day * 2 + 1]);
        return sb;
    }

    public static StringBuilder formatHH(TemporalAccessor time, StringBuilder sb) {
        int hour = time.get(ChronoField.HOUR_OF_DAY);
        return appendSmallInt(sb, hour);
    }

    public static StringBuilder formatmm(TemporalAccessor time, StringBuilder sb) {
        int minute = time.get(ChronoField.MINUTE_OF_HOUR);
        return appendSmallInt(sb, minute);
    }

    public static StringBuilder formatss(TemporalAccessor time, StringBuilder sb) {
        int seconds = time.get(ChronoField.SECOND_OF_MINUTE);
        return appendSmallInt(sb, seconds);
    }

    public static StringBuilder formatSSS(TemporalAccessor time, StringBuilder sb) {
        int millis = time.get(ChronoField.MILLI_OF_SECOND);
        if (millis < 100) {
            sb.append('0');
            if (millis < 10) {
                sb.append('0');
            }
        }
        return sb.append(millis);
    }
}

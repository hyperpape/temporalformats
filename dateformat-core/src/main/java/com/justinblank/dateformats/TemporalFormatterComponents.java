package com.justinblank.dateformats;

import com.justinblank.classcompiler.lang.Builtin;
import com.justinblank.classcompiler.lang.ReferenceType;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

import static com.justinblank.classcompiler.lang.BinaryOperator.lt;
import static com.justinblank.classcompiler.lang.CodeElement.*;
import static com.justinblank.classcompiler.lang.CodeElement.read;

public class TemporalFormatterComponents {

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
        if (month < 10) {
            sb.append('0');
        }
        return sb.append(month);
    }

    public static StringBuilder formatdd(TemporalAccessor time, StringBuilder sb) {
        int day = time.get(ChronoField.DAY_OF_MONTH);
        if (day < 10) {
            sb.append('0');
        }
        return sb.append(day);
    }

    public static StringBuilder formatHH(TemporalAccessor time, StringBuilder sb) {
        int hour = time.get(ChronoField.HOUR_OF_DAY);
        if (hour < 10) {
            sb.append('0');
        }
        return sb.append(hour);
    }

    public static StringBuilder formatmm(TemporalAccessor time, StringBuilder sb) {
        int minute = time.get(ChronoField.MINUTE_OF_HOUR);
        if (minute < 10) {
            sb.append('0');
        }
        return sb.append(minute);
    }

    public static StringBuilder formatss(TemporalAccessor time, StringBuilder sb) {
        int seconds = time.get(ChronoField.SECOND_OF_MINUTE);
        if (seconds < 10) {
            sb.append('0');
        }
        return sb.append(seconds);
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

package com.justinblank.temporalformatter;

import java.time.temporal.ChronoField;
import java.util.Objects;

class FormatSpecifier {

    final String formatString;
    final ChronoField chronoField;
    final Character divider;
    final int minDigits;
    final int maxDigits;

    private FormatSpecifier(String s) {
        formatString = s;
        chronoField = null;
        minDigits = -1;
        maxDigits = -1;
        divider = null;
    }

    private FormatSpecifier(ChronoField field, int minDigits, int maxDigits, Character divider) {
        formatString = null;
        chronoField = field;
        this.minDigits = minDigits;
        this.maxDigits = maxDigits;
        this.divider = divider;

    }

    static FormatSpecifier ofString(String s) {
        Objects.requireNonNull(s, "pattern specifier cannot be null");
        // TODO: empties? 
        return new FormatSpecifier(s);
    }

    static FormatSpecifier fieldSpecifier(ChronoField field, int minDigits, int maxDigits, Character divider) {
        Objects.requireNonNull(field, "ChronoField cannot be null");
        if (minDigits < 0) {
            throw new IllegalArgumentException("minDigits cannot be less than 0");
        }
        if (maxDigits < minDigits) {
            throw new IllegalArgumentException("maxDigits must be less than minDigits");
        }
        return new FormatSpecifier(field, minDigits, maxDigits, divider);
    }

    @Override
    public String toString() {
        if (formatString != null) {
            return formatString;
        }
        else {
            return chronoField.toString() + "{" + minDigits + "," + maxDigits + "}," + divider;
        }
    }
}

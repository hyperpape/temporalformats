package com.justinblank.temporalformatter;

import java.text.ParseException;
import java.util.*;

public class TemporalFormatterPatternParser {

    static final Set<String> SUPPORTED_COMPONENTS;

    static {
        Set<String> supported = new HashSet<>();
        supported.add("yyyy");
        supported.add("MM");
        supported.add("dd");
        supported.add("HH");
        supported.add("mm");
        supported.add("ss");
        supported.add("SSS");
        supported.add("XXX");
        supported.add("-");
        supported.add(".");
        supported.add(":");
        supported.add("/");
        supported.add(" ");
        supported.add("T");
        SUPPORTED_COMPONENTS = new HashSet<>(supported);
    }

    public static List<FormatSpecifier> splitToComponents(String string) throws ParseException {
        Objects.requireNonNull(string, "Cannot parse a null dateformat string");
        List<FormatSpecifier> components = new ArrayList<>();
        var i = 0;
        // YOLO: O(n^2)
        while (i < string.length()) {
            var lastStart = i;
            for (var candidate : SUPPORTED_COMPONENTS) {
                if (i + candidate.length() <= string.length()) {
                    if (string.substring(i, i + candidate.length()).equals(candidate)) {
                        components.add(FormatSpecifier.ofString(candidate));
                        i += candidate.length();
                        break;
                    }
                }
            }
            if (i == lastStart) {
                throw new ParseException("Couldn't recognize component of date string", i);
            }
        }
        return components;
    }

}

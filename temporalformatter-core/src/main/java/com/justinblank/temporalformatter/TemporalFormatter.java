package com.justinblank.temporalformatter;

import java.time.temporal.TemporalAccessor;

public interface TemporalFormatter {

    String format(TemporalAccessor temporal);
}

package com.justinblank.temporalformatter;

import java.time.temporal.TemporalAccessor;

/**
 * A formatter for TemporalAccessor instances. Implementations of this class must be thread safe.
 */
public interface TemporalFormatter {

    String format(TemporalAccessor temporal);
}

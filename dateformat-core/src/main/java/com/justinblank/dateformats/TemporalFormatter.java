package com.justinblank.dateformats;

import java.time.temporal.TemporalAccessor;

public interface TemporalFormatter {

    String format(TemporalAccessor temporal);
}

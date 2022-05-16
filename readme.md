# TemporalFormatters

![Badge](https://www.repostatus.org/badges/latest/wip.svg)

Experiment with generating java classes on the fly for date formatting. 

The DateTimeFormatter in the standard library parses the dateformat into a set of objects, which are successively called
to parse or generate output. This suggests an optimization of generating code specified to the particular
format, and that's what this library does.

Preliminary results suggest it's significantly faster than the standard library DateTimeFormatter class.

## Usage

You can generate formatters from a format pattern. Supported patterns have the same semantics as the DateTimeFormatter
from the standard library. Not all of its pattern elements are supported yet, with the supported options enumerated in
TemporalFormatterPatternParser.

```Java
TemporalFormatter formatter = TemporalFormatCreator.generateFormatter("yyyy-MM-dd");
formatter.format(Instant.now()); // "2022-04-21"
```

The library also provides some builtin formatters:

```Java
StandardFormats.ISO_LOCAL_DATE_TIME.format(Instant.now());
StandardFormats.ISO_LOCAL_TIME.format(Instant.now());
StandardFormats.ISO_LOCAL_DATE.format(Instant.now());
```

Parsing strings into date/time values is not currently supported, but will probably be implemented in the future.

## Building

Requires Java 11, builds with maven. 

The generated classes are built with Java 8, so they can be precompiled, and included in an application running java 8.

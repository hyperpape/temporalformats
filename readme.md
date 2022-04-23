# DateFormats

![Badge](https://www.repostatus.org/badges/latest/wip.svg)

Experiment with generating java classes on the fly for DateFormats. 

The dateformatters in the standard library parse the dateformat into a set of objects, which are successively called to
parse or generate output. This suggests an optimization of generating code specified to the particular 
format, and that's what this library does.

Preliminary results suggest it's significantly faster than the standard library DateTimeFormatter class.

## Usage

```Java
TemporalFormatter formatter = DateFormatCreator.generateFormatter("yyyy-MM-dd");
formatter.format(Instant.now()); // "2022-04-21"
```

Parsing is not currently supported, but will probably be implemented in the future.
## Building

Requires Java 11, builds with maven. 

Currently depends on having [Mako](https://github.com/hyperpape/mako) locally installed, as I have yet to push that 
library to maven central.  
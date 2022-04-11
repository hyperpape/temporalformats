# DateFormats

![Badge](https://travis-ci.com/hyperpape/dateformats.svg?branch=main)
![Badge](https://www.repostatus.org/badges/latest/wip.svg)

Experiment with generating java classes on the fly for DateFormats. 

The dateformatters in the standard library parse the dateformat into a set of objects, which are successively called to
parse or generate output. This suggests an optimization of generating code specified to the particular 
format, and that's what this library does.

## Building

Requires Java 11, builds with maven. 

Currently depends on having [Mako](https://github.com/hyperpape/mako) locally installed, as I have yet to push that 
library to maven central.  
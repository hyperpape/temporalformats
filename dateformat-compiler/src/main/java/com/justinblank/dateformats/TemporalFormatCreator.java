package com.justinblank.dateformats;

import com.justinblank.classcompiler.*;
import com.justinblank.classcompiler.lang.Builtin;
import com.justinblank.classcompiler.lang.ReferenceType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.justinblank.classcompiler.lang.BinaryOperator.*;
import static com.justinblank.classcompiler.lang.CodeElement.*;
import static com.justinblank.classcompiler.lang.Literal.literal;
import static com.justinblank.classcompiler.lang.UnaryOperator.not;

public class TemporalFormatCreator {

    private static final AtomicInteger CLASS_NUMBER = new AtomicInteger();

    public TemporalFormatter generateFormatter(String formatString) throws ParseException {
        return generateFormatter(TemporalFormatterPatternParser.splitToComponents(formatString), nextClassName());
    }

    public TemporalFormatter generateFormatter(String formatString, String className) throws ParseException {
        return generateFormatter(TemporalFormatterPatternParser.splitToComponents(formatString), className);
    }

    public TemporalFormatter generateFormatter(List<FormatSpecifier> formatStrings) {
        return generateFormatter(formatStrings, nextClassName());
    }

    public TemporalFormatter generateFormatter(List<FormatSpecifier> formatStrings, String name) {
        try {
            ClassBuilder classBuilder = new ClassBuilder(name,
                    "java/lang/Object",
                    new String[]{"com/justinblank/dateformats/TemporalFormatter"});
            classBuilder.addEmptyConstructor();
            // TODO: determine if matters whether we use literal strings or chars
            classBuilder.addConstant("SLASH", CompilerUtil.STRING_DESCRIPTOR, "/");
            classBuilder.addConstant("COLON", CompilerUtil.STRING_DESCRIPTOR, ":");
            classBuilder.addConstant("DASH", CompilerUtil.STRING_DESCRIPTOR, "-");
            classBuilder.addConstant("T", CompilerUtil.STRING_DESCRIPTOR, "T");
            classBuilder.addConstant("ZERO", CompilerUtil.STRING_DESCRIPTOR, "0");
            classBuilder.addConstant("SPACE", CompilerUtil.STRING_DESCRIPTOR, " ");
            classBuilder.addConstant("DOT", CompilerUtil.STRING_DESCRIPTOR, ".");
            classBuilder.addConstant("Z", CompilerUtil.STRING_DESCRIPTOR, "Z");
            classBuilder.addConstant("PLUS", CompilerUtil.STRING_DESCRIPTOR, "+");
            Vars vars = new GenericVars("time", "sb", "field", "extraString"); // TODO: names/optional argument
            generateFormatMethod(formatStrings, classBuilder, vars);
            Class<?> cls = new ClassCompiler(classBuilder).generateClass();
            return (TemporalFormatter) cls.getConstructors()[0].newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generateFormatMethod(List<FormatSpecifier> formatStrings, ClassBuilder classBuilder, Vars vars) {
        List<String> arguments = new ArrayList<>();
        arguments.add(CompilerUtil.descriptor(TemporalAccessor.class));
        Method method = classBuilder.mkMethod("format", arguments, CompilerUtil.STRING_DESCRIPTOR, vars);
        // TODO: avoid over-allocating here
        method.set("sb", construct(ReferenceType.of(StringBuilder.class), literal(32)));
        for (var fs : formatStrings) {
            if (fs.formatString != null) {
                switch (fs.formatString) {
                    case "yyyy":
                        method.set("field", mod(callInterface("get", Builtin.I, read("time"),
                                getStatic("YEAR", ReferenceType.of(ChronoField.class),
                                        ReferenceType.of(ChronoField.class))), 10000));
                        method.cond(lt(read("field"), 1000))
                                .withBody(List.of(call("append", StringBuilder.class, read("sb"),
                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))),
                                        cond(lt(read("field"), 100)).withBody(
                                                List.of(call("append", StringBuilder.class, read("sb"),
                                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))),
                                                        cond(lt(read("field"), 10)).withBody(call("append", StringBuilder.class, read("sb"),
                                                                getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))))))));
                        method.add(call("append", StringBuilder.class, read("sb"),
                                read("field")));
                        break;
                    case "MM":
                        method.set("field", callInterface("get", Builtin.I, read("time"),
                                getStatic("MONTH_OF_YEAR", ReferenceType.of(ChronoField.class),
                                        ReferenceType.of(ChronoField.class))));
                        method.cond(lt(read("field"), 10)).withBody(
                                call("append", StringBuilder.class,
                                        read("sb"),
                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))));
                        method.add(call("append", StringBuilder.class, read("sb"), read("field")));

                        break;
                    case "dd":
                        method.set("field", callInterface("get", Builtin.I, read("time"),
                                getStatic("DAY_OF_MONTH", ReferenceType.of(ChronoField.class),
                                        ReferenceType.of(ChronoField.class))));
                        method.cond(lt(read("field"), 10)).withBody(
                                call("append", StringBuilder.class,
                                        read("sb"),
                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))));
                        method.add(call("append", StringBuilder.class, read("sb"), read("field")));

                        break;
                    case "HH":
                        method.set("field", callInterface("get", Builtin.I, read("time"),
                                getStatic("HOUR_OF_DAY", ReferenceType.of(ChronoField.class),
                                        ReferenceType.of(ChronoField.class))));
                        method.cond(lt(read("field"), 10)).withBody(
                                call("append", StringBuilder.class,
                                        read("sb"),
                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))));
                        method.add(call("append", StringBuilder.class, read("sb"), read("field")));

                        break;
                    case "mm":
                        method.set("field", callInterface("get", Builtin.I, read("time"),
                                getStatic("MINUTE_OF_HOUR", ReferenceType.of(ChronoField.class),
                                        ReferenceType.of(ChronoField.class))));
                        method.cond(lt(read("field"), 10)).withBody(
                                call("append", StringBuilder.class,
                                        read("sb"),
                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))));
                        method.add(call("append", StringBuilder.class, read("sb"), read("field")));


                        break;
                    case "ss":
                        method.set("field", callInterface("get", Builtin.I, read("time"),
                                getStatic("SECOND_OF_MINUTE", ReferenceType.of(ChronoField.class),
                                        ReferenceType.of(ChronoField.class))));
                        method.cond(lt(read("field"), 10)).withBody(
                                call("append", StringBuilder.class,
                                        read("sb"),
                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))));
                        method.add(call("append", StringBuilder.class, read("sb"), read("field")));
                        break;
                    case "SSS":
                        method.set("field", callInterface("get", Builtin.I, read("time"),
                                getStatic("MILLI_OF_SECOND", ReferenceType.of(ChronoField.class),
                                        ReferenceType.of(ChronoField.class))));
                        method.cond(lt(read("field"), 100)).withBody(
                                call("append", StringBuilder.class,
                                        read("sb"),
                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))));
                        method.cond(lt(read("field"), 10)).withBody(
                                call("append", StringBuilder.class,
                                        read("sb"),
                                        getStatic("ZERO", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))));
                        method.add(call("append", StringBuilder.class, read("sb"), read("field")));
                        break;
                    case "XXX":
                        // TODO: adding the proper method name here results in truncated class file exception--why?!
                        if (!classBuilder.hasMethod("")) {
                            addOffsetMethod(classBuilder);
                        }
                        method.call("appendOffset",
                                ReferenceType.of(StringBuilder.class), thisRef(), read("sb"), read("time"));
                        break;
                    case " ":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("SPACE", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class)));
                        break;
                    case "T":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("T", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class)));
                        break;
                    case "-":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("DASH", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class)));
                        break;
                    case ".":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("DOT", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class)));
                        break;
                    case ":":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("COLON", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class)));
                        break;
                }
            } else {
                if (!classBuilder.hasMethod("convertToFraction")) {
                    addConvertToFractionMethod(classBuilder, fs);
                }
                var chronoField = fs.chronoField;

                method.set("field", callInterface("get", Builtin.I, read("time"),
                    getStatic(chronoField.name(), ReferenceType.of(ChronoField.class),
                            ReferenceType.of(ChronoField.class))));
                method.cond(not(eq(read("field"), 0))).withBody(List.of(
                        call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                // TODO: shouldn't hard-code dot, should switch on fs class
                                getStatic("DOT", ReferenceType.of(classBuilder.getClassName()), ReferenceType.of(String.class))),
                        set("extraString", call("convertToFraction", ReferenceType.of(String.class), thisRef(),
                                read("field"))),
                        call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                call("substring", ReferenceType.of(String.class), read("extraString"),
                                        callStatic(Math.class, "min", Builtin.I, literal(2),
                                                call("length", Builtin.I, read("extraString")))))
                ));
            }
        }
        method.returnValue(call("toString", ReferenceType.of(String.class), read("sb")));
    }

    private void addConvertToFractionMethod(ClassBuilder classBuilder, FormatSpecifier fs) {
        List<String> arguments = new ArrayList<>();
        arguments.add("I");
        var vars = new GenericVars("value", "bd");
        var method = classBuilder.mkMethod("convertToFraction", arguments,
                CompilerUtil.descriptor(String.class), vars);
        var constructValue = callStatic(BigDecimal.class, "valueOf", ReferenceType.of(BigDecimal.class), cast(Builtin.L, read("value")));
        var constructRange = callStatic(BigDecimal.class, "valueOf", ReferenceType.of(BigDecimal.class), cast(Builtin.L, literal((int) Math.pow(10, fs.maxDigits))));
        method.set("bd", call("divide", ReferenceType.of(BigDecimal.class), constructValue, constructRange, literal(9),
                getStatic("FLOOR", ReferenceType.of(RoundingMode.class), ReferenceType.of(RoundingMode.class))));
        method.cond(eq(getStatic("ZERO", ReferenceType.of(BigDecimal.class),
                ReferenceType.of(BigDecimal.class)), read("bd")))
                .withBody(call("toPlainString", ReferenceType.of(String.class),
                        getStatic("ZERO", ReferenceType.of(BigDecimal.class),
                                ReferenceType.of(BigDecimal.class))));
        method.returnValue(call("toPlainString", ReferenceType.of(String.class),
                call("stripTrailingZeros", ReferenceType.of(BigDecimal.class), read("bd"))));
    }

    private void addOffsetMethod(ClassBuilder classBuilder) {
        List<String> arguments = new ArrayList<>();
        arguments.add(CompilerUtil.descriptor(StringBuilder.class));
        arguments.add(CompilerUtil.descriptor(TemporalAccessor.class));
        var vars = new GenericVars("sb", "time", "field", "absHours", "absMinutes");
        var method = classBuilder.mkMethod("appendOffset", arguments,
                CompilerUtil.descriptor(StringBuilder.class), vars);
        method.set("field", callStatic(CompilerUtil.internalName(Math.class), "toIntExact", Builtin.I,
                cast(Builtin.L, callInterface("get", Builtin.I, read("time"),
                        getStatic("OFFSET_SECONDS", ReferenceType.of(ChronoField.class),
                                ReferenceType.of(ChronoField.class))))));
        // output +/-/Z
        method.cond(eq(read("field"), 0))
                .withBody(returnValue(call("append", StringBuilder.class,
                        read("sb"),
                        getStatic("Z", ReferenceType.of(classBuilder.getClassName()),
                                ReferenceType.of(String.class)))));
        method.cond(lt(read("field"), 0))
                .withBody(call("append", StringBuilder.class,
                        read("sb"),
                        getStatic("DASH", ReferenceType.of(classBuilder.getClassName()),
                                ReferenceType.of(String.class))));
        method.cond(gt(read("field"), 0))
                .withBody(
                        call("append", StringBuilder.class, read("sb"),
                                getStatic("PLUS", ReferenceType.of(classBuilder.getClassName()),
                                        ReferenceType.of(String.class))));
        // append hour offset
        method.set("absHours", callStatic(CompilerUtil.internalName(Math.class), "abs", Builtin.I,
                mod(div(read("field"), 3600), 100)));
        method.call("append", StringBuilder.class, read("sb"),
                // TODO: appending an int rather than a char is suboptimal here--DateTimeFormatter uses some cute char
                // math
                div(read("absHours"),10));
        method.call("append", StringBuilder.class, read("sb"),
                mod(read("absHours"), 10));
        // append minutes
        method.call("append", StringBuilder.class, read("sb"),
                getStatic("COLON", ReferenceType.of(classBuilder.getClassName()),
                        ReferenceType.of(String.class)));
        method.set("absMinutes", callStatic(CompilerUtil.internalName(Math.class), "abs", Builtin.I,
                mod(div(read("field"), 60), 60)));
        method.call("append", StringBuilder.class, read("sb"),
                // TODO: appending an int rather than a char is suboptimal here--DateTimeFormatter uses some cute char
                // math
                div(read("absMinutes"),10));
        method.call("append", StringBuilder.class, read("sb"),
                mod(read("absMinutes"), 10));
        method.returnValue(read("sb"));
    }

    private static String nextClassName() {
        return "DateFormat" + CLASS_NUMBER.incrementAndGet();
    }
}

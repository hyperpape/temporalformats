package com.justinblank.temporalformatter;

import com.justinblank.classcompiler.*;
import com.justinblank.classcompiler.lang.Builtin;
import com.justinblank.classcompiler.lang.ReferenceType;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
    public static final String DEFAULT_PACKAGE = "com.justinblank.temporalformatter";

    private static final Map<String, String> CONSTANTS = new HashMap<>();
    private static final Map<String, String> DIVIDERS_TO_CONSTANTS = new HashMap<>();

    static {
        CONSTANTS.put("SLASH", "/");
        CONSTANTS.put("COLON", ":");
        CONSTANTS.put("DASH", "-");
        CONSTANTS.put("T", "T");
        CONSTANTS.put("ZERO", "0");
        CONSTANTS.put("SPACE", " ");
        CONSTANTS.put("DOT", ".");
        CONSTANTS.put("Z", "Z");
        CONSTANTS.put("PLUS", "+");

        for (var e : CONSTANTS.entrySet()) {
            DIVIDERS_TO_CONSTANTS.put(e.getValue(), e.getKey());
        }
    }

    /**
     * Generate a TemporalFormatter class for the given list of FormatSpecifier instances.
     * @param formatString the format string, e.g. "yyyy-MM-dd". See TemporalFormatterPatternParser for supported
     *                     components
     * @return an instance of the TemporalFormatter generated
     */
    public static TemporalFormatter generateFormatter(String formatString) throws ParseException {
        return generateFormatter(TemporalFormatterPatternParser.splitToComponents(formatString),
                DEFAULT_PACKAGE, nextClassName());
    }

    /**
     * Generate a TemporalFormatter class for the given list of FormatSpecifier instances.
     * @param formatString the specifiers
     * @param className the name of the generated class
     * @return an instance of the TemporalFormatter generated
     */
    public static TemporalFormatter generateFormatter(String formatString, String packageName, String className) throws ParseException {
        return generateFormatter(TemporalFormatterPatternParser.splitToComponents(formatString), packageName, className);
    }

    /**
     * Generate a TemporalFormatter class for the given list of FormatSpecifier instances.
     * @param formatSpecifiers the specifiers
     * @return an instance of the TemporalFormatter generated
     */
    public static TemporalFormatter generateFormatter(List<FormatSpecifier> formatSpecifiers) {
        return generateFormatter(formatSpecifiers, DEFAULT_PACKAGE, nextClassName());
    }

    /**
     * Generate a TemporalFormatter class for the given list of FormatSpecifier instances.
     * @param formatSpecifiers the specifiers
     * @param className the name of the generated class
     * @return an instance of the TemporalFormatter generated
     */
    public static TemporalFormatter generateFormatter(List<FormatSpecifier> formatSpecifiers, String packageName, String className) {
        try {
            ClassBuilder classBuilder = prepareTemporalFormatterClassBuilder(formatSpecifiers, packageName, className);
            Class<?> cls = new ClassCompiler(classBuilder).generateClass();
            return (TemporalFormatter) cls.getConstructors()[0].newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate and write a TemporalFormatter classFile to the specified location
     * @param formatSpecifiers the list of FormatSpecifiers for the formatter
     * @param className the name of the generated class
     * @param fileName the location of the file to be written
     * @throws IOException in case the file cannot be written
     */
    public static void writeTemporalFormatterClassFile(List<FormatSpecifier> formatSpecifiers, String packageName, String className, String fileName)
            throws IOException {
        ClassBuilder classBuilder = prepareTemporalFormatterClassBuilder(formatSpecifiers, packageName, className);
        byte[] bytes = new ClassCompiler(classBuilder).generateClassAsBytes();
        Files.write(Path.of(fileName), bytes, StandardOpenOption.CREATE);
    }

    /**
     * Generate a class formatter for the passed FormatSpecifiers, with the passed className
     * @param formatSpecifiers the list of FormatSpecifiers to be used
     * @param className the name of the generated class
     * @return a ClassBuilder instance that is ready to be compiled to bytecode
     */
    static ClassBuilder prepareTemporalFormatterClassBuilder(List<FormatSpecifier> formatSpecifiers, String packageName, String className) {
        ClassBuilder classBuilder = new ClassBuilder(className, packageName,
                "java/lang/Object",
                new String[]{"com/justinblank/temporalformatter/TemporalFormatter"});
        classBuilder.addEmptyConstructor();
        for (var entry : CONSTANTS.entrySet()) {
            classBuilder.addConstant(entry.getKey(), CompilerUtil.STRING_DESCRIPTOR, entry.getValue());
        }
        Vars vars = new GenericVars("time", "sb", "field", "extraString"); // TODO: names/optional argument
        generateFormatMethod(formatSpecifiers, classBuilder, vars);
        return classBuilder;
    }

    private static void generateFormatMethod(List<FormatSpecifier> formatStrings, ClassBuilder classBuilder, Vars vars) {
        List<String> arguments = new ArrayList<>();
        arguments.add(CompilerUtil.descriptor(TemporalAccessor.class));
        Method method = classBuilder.mkMethod("format", arguments, CompilerUtil.STRING_DESCRIPTOR, vars);
        // TODO: avoid over-allocating here
        method.set("sb", construct(ReferenceType.of(StringBuilder.class), literal(32)));
        for (var fs : formatStrings) {
            if (fs.formatString != null) {
                switch (fs.formatString) {
                    case "yyyy":
                        method.callStatic(CompilerUtil.internalName(TemporalFormatterComponents.class),
                                "formatyyyy", ReferenceType.of(StringBuilder.class), read("time"),
                                read("sb"));
                        break;
                    case "MM":
                        method.callStatic(CompilerUtil.internalName(TemporalFormatterComponents.class),
                                "formatMM", ReferenceType.of(StringBuilder.class), read("time"),
                                read("sb"));
                        break;
                    case "dd":
                        method.callStatic(CompilerUtil.internalName(TemporalFormatterComponents.class),
                                "formatdd", ReferenceType.of(StringBuilder.class), read("time"),
                                read("sb"));
                        break;
                    case "HH":
                        method.callStatic(CompilerUtil.internalName(TemporalFormatterComponents.class),
                                "formatHH", ReferenceType.of(StringBuilder.class), read("time"),
                                read("sb"));
                        break;
                    case "mm":
                        method.callStatic(CompilerUtil.internalName(TemporalFormatterComponents.class),
                                "formatmm", ReferenceType.of(StringBuilder.class), read("time"),
                                read("sb"));
                        break;
                    case "ss":
                        method.callStatic(CompilerUtil.internalName(TemporalFormatterComponents.class),
                                "formatss", ReferenceType.of(StringBuilder.class), read("time"),
                                read("sb"));
                        break;
                    case "SSS":
                        method.callStatic(CompilerUtil.internalName(TemporalFormatterComponents.class),
                                "formatSSS", ReferenceType.of(StringBuilder.class), read("time"),
                                read("sb"));
                        break;
                    case "XXX":
                        if (!classBuilder.hasMethod("appendOffset")) {
                            addOffsetMethod(classBuilder);
                        }
                        method.call("appendOffset",
                                ReferenceType.of(StringBuilder.class), thisRef(), read("sb"), read("time"));
                        break;
                    case " ":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("SPACE", ReferenceType.of(classBuilder.getFQCN()), ReferenceType.of(String.class)));
                        break;
                    case "T":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("T", ReferenceType.of(classBuilder.getFQCN()), ReferenceType.of(String.class)));
                        break;
                    case "-":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("DASH", ReferenceType.of(classBuilder.getFQCN()), ReferenceType.of(String.class)));
                        break;
                    case ".":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("DOT", ReferenceType.of(classBuilder.getFQCN()), ReferenceType.of(String.class)));
                        break;
                    case ":":
                        method.call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic("COLON", ReferenceType.of(classBuilder.getFQCN()), ReferenceType.of(String.class)));
                        break;
                }
            } else {
                if (!classBuilder.hasMethod("convertToFraction")) {
                    addConvertToFractionMethod(classBuilder, fs);
                }
                var chronoField = fs.chronoField;
                // TODO: determine if we need to handle dividers outside of our currently supported set
                var dividerConstant = DIVIDERS_TO_CONSTANTS.get(String.valueOf(fs.divider));
                if (dividerConstant == null) {
                    throw new IllegalArgumentException("Divider " + fs.divider + " not recognized");
                }

                method.set("field", callInterface("get", Builtin.I, read("time"),
                    getStatic(chronoField.name(), ReferenceType.of(ChronoField.class),
                            ReferenceType.of(ChronoField.class))));
                method.cond(not(eq(read("field"), 0))).withBody(List.of(
                        call("append", ReferenceType.of(StringBuilder.class), read("sb"),
                                getStatic(dividerConstant, ReferenceType.of(classBuilder.getFQCN()), ReferenceType.of(String.class))),
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

    private static void addConvertToFractionMethod(ClassBuilder classBuilder, FormatSpecifier fs) {
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

    private static void addOffsetMethod(ClassBuilder classBuilder) {
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
        return "TemporalFormatter" + CLASS_NUMBER.incrementAndGet();
    }
}

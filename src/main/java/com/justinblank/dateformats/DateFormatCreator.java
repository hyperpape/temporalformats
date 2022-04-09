package com.justinblank.dateformats;

import com.justinblank.classcompiler.*;
import com.justinblank.classcompiler.lang.Builtin;
import com.justinblank.classcompiler.lang.CodeElement;
import com.justinblank.classcompiler.lang.Expression;
import com.justinblank.classcompiler.lang.ReferenceType;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.justinblank.classcompiler.lang.CodeElement.*;
import static com.justinblank.classcompiler.lang.Literal.literal;

public class DateFormatCreator {

    private static final AtomicInteger CLASS_NUMBER = new AtomicInteger();

    public TemporalFormatter generateFormatter(List<String> formatStrings) {

        try {
            ClassBuilder classBuilder = new ClassBuilder("DateFormat" + CLASS_NUMBER.incrementAndGet(),
                    "java/lang/Object",
                    new String[]{"com/justinblank/dateformats/TemporalFormatter"});
            classBuilder.addEmptyConstructor();

            Vars vars = new GenericVars("time", "sb", "field");
            generateFormatMethod(formatStrings, classBuilder, vars);
            Class<?> cls = new ClassCompiler(classBuilder).generateClass();
            return (TemporalFormatter) cls.getConstructors()[0].newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generateFormatMethod(List<String> formatStrings, ClassBuilder classBuilder, Vars vars) {
        List<String> arguments = new ArrayList<>();
        arguments.add(CompilerUtil.descriptor(TemporalAccessor.class));
        Method method = classBuilder.mkMethod("format", arguments, CompilerUtil.STRING_DESCRIPTOR, vars);
        method.set("sb", construct(ReferenceType.of(StringBuilder.class), literal(32)));
        Map<String, String> constants = new HashMap<>();
        for (String s : formatStrings) {
            switch (s) {
                case "yyyy":
                    method.add(call("append", StringBuilder.class, read("sb"),
                            callInterface("get", Builtin.I, read("time"),
                                    getStatic("YEAR", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class)))));
                    break;
                case "MM":
                    method.add(call("append", StringBuilder.class, read("sb"),
                            callInterface("get", Builtin.I, read("time"),
                                    getStatic("MONTH_OF_YEAR", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class)))));
                    break;
                case "dd":
                    method.add(call("append", StringBuilder.class, read("sb"),
                            callInterface("get", Builtin.I, read("time"),
                                    getStatic("DAY_OF_MONTH", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class)))));
                    break;
                case "HH":
                    method.add(call("append", StringBuilder.class, read("sb"),
                            callInterface("get", Builtin.I, read("time"),
                                    getStatic("HOUR_OF_DAY", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class)))));
                    break;
                case "mm":
                    method.add(call("append", StringBuilder.class, read("sb"),
                            callInterface("get", Builtin.I, read("time"),
                                    getStatic("MINUTE_OF_HOUR", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class)))));
                    break;
                case "ss":
                    method.add(call("append", StringBuilder.class, read("sb"),
                            callInterface("get", Builtin.I, read("time"),
                                    getStatic("SECOND_OF_MINUTE", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class)))));
                    break;
                case "SSS":
                    call("append", StringBuilder.class, read("sb"),
                            callInterface("get", Builtin.I, read("time"),
                                    getStatic("MILLI_OF_SECOND", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class))));
                            break;
                case "XXX":
                    break;
                case "T":
                    method.call("append", ReferenceType.of(String.class), read("sb"),
                            getStatic("T", ReferenceType.of(String.class), ReferenceType.of(String.class)));
                    break;
                case "-":
                    method.call("append", ReferenceType.of(String.class), read("sb"),
                            getStatic("DASH", ReferenceType.of(String.class), ReferenceType.of(String.class)));
                    break;
                case ":":
                    method.call("append", ReferenceType.of(String.class), read("sb"),
                            getStatic("COLON", ReferenceType.of(String.class), ReferenceType.of(String.class)));
                    break;
            }
        }
        method.returnValue(call("toString", ReferenceType.of(String.class), read("sb")));
    }
}

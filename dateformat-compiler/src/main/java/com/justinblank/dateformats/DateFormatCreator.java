package com.justinblank.dateformats;

import com.justinblank.classcompiler.*;
import com.justinblank.classcompiler.lang.Builtin;
import com.justinblank.classcompiler.lang.ReferenceType;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.justinblank.classcompiler.lang.BinaryOperator.*;
import static com.justinblank.classcompiler.lang.CodeElement.*;
import static com.justinblank.classcompiler.lang.Literal.literal;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

public class DateFormatCreator {

    private static final AtomicInteger CLASS_NUMBER = new AtomicInteger();

    public TemporalFormatter generateFormatter(List<String> formatStrings) {

        try {
            ClassBuilder classBuilder = new ClassBuilder("DateFormat" + CLASS_NUMBER.incrementAndGet(),
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
        for (String s : formatStrings) {
            switch (s) {
                case "yyyy":
                    // TODO: padding
                    method.add(call("append", StringBuilder.class, read("sb"),
                            callInterface("get", Builtin.I, read("time"),
                                    getStatic("YEAR", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class)))));
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
        }
        method.returnValue(call("toString", ReferenceType.of(String.class), read("sb")));
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
                // TODO: appending an int rather than a char is suboptimal here
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
                // TODO: appending an int rather than a char is suboptimal here
                div(read("absMinutes"),10));
        method.call("append", StringBuilder.class, read("sb"),
                mod(read("absMinutes"), 10));

        // totalSeconds = time.get(ChronoField.getOffsetSeconds());
        // if (totalSeconds < 0) { sb.append('-'); } else { sb.append('+') };
        //
        //     int absHours = Math.abs((totalSecs / 3600) % 100);  // anything larger than 99 silently dropped
        //                int absMinutes = Math.abs((totalSecs / 60) % 60);
        //                int absSeconds = Math.abs(totalSecs % 60);
        //                int bufPos = buf.length();
        //                int output = absHours;
        //                buf.append(totalSecs < 0 ? "-" : "+");
        //                if (isPaddedHour() || absHours >= 10) {
        //                    formatZeroPad(false, absHours, buf);
        //                } else {
        //                    buf.append((char) (absHours + '0'));
        //                }
        //   private void formatZeroPad(boolean colon, int value, StringBuilder buf) {
        //            buf.append(colon ? ":" : "")
        //                    .append((char) (value / 10 + '0'))
        //                    .append((char) (value % 10 + '0'));
        //        }
        method.returnValue(read("sb"));
    }
}

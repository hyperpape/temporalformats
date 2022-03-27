package com.justinblank.dateformats;

import com.justinblank.classcompiler.*;
import com.justinblank.classcompiler.lang.Builtin;
import com.justinblank.classcompiler.lang.CodeElement;
import com.justinblank.classcompiler.lang.Expression;
import com.justinblank.classcompiler.lang.ReferenceType;

import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.justinblank.classcompiler.lang.CodeElement.*;
import static com.justinblank.classcompiler.lang.Literal.literal;

public class DateFormatCreator {

    private static final AtomicInteger CLASS_NUMBER = new AtomicInteger();

    public TemporalFormatter generateFormatter(List<String> formatStrings) {
        ClassBuilder classBuilder = new ClassBuilder("DateFormat" + CLASS_NUMBER.incrementAndGet(),
                "java/lang/Object",
                new String[]{"com/justinblank/dateformats/TemporalFormatter"});
        classBuilder.addEmptyConstructor();
        Vars vars = new GenericVars("sb", "field");
        generateFormatMethod(formatStrings, classBuilder, vars);
        Class<?> cls = new ClassCompiler(classBuilder).generateClass();
        return null;
    }

    private void generateFormatMethod(List<String> formatStrings, ClassBuilder classBuilder, Vars vars) {
        List<String> arguments = new ArrayList<>();
        arguments.add(CompilerUtil.descriptor(TemporalAccessor.class));
        Method method = classBuilder.mkMethod("format", arguments, CompilerUtil.STRING_DESCRIPTOR, vars);
        method.set("sb", construct(ReferenceType.of(StringBuilder.class), literal(32)));
        Expression exp = read("sb");
        for (String s : formatStrings) {
            switch (s) {
                case "yyyy":
                case "MM":
                case "dd":
                case "HH":
                case "mm":
                case "ss":
                case "SSS":
                    exp = call("append", ReferenceType.of(StringBuilder.class), exp,
                            call("get", ReferenceType.of(TemporalAccessor.class), read("1"),
                                    getStatic("MILLI_OF_SECOND", ReferenceType.of(ChronoField.class),
                                            ReferenceType.of(ChronoField.class))));
                            break;
                case "XXX":

                case "T":
                case "-":
                case ":":
                    // TODO:
                    exp = call("append", ReferenceType.of(StringBuilder.class), exp, (Expression) null);
            }
        }
        method.add(exp);
        method.returnValue(call("toString", ReferenceType.of(String.class), read("sb")));
    }
}

package ru.clevertec.check.annotations.printer;

import org.springframework.context.annotation.Conditional;
import ru.clevertec.check.condition.PrinterCondition;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(PrinterCondition.class)
public @interface PrinterAnnotation {
}

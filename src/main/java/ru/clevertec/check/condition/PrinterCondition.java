package ru.clevertec.check.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class PrinterCondition implements Condition {
    public static final String clevertec_Template = "templates/Clevertec_Template.pdf";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getResourceLoader().getResource(clevertec_Template).exists();
    }
}

package ru.clevertec.check.aspects;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.clevertec.check.annotations.log.LogLevel;
import ru.clevertec.check.annotations.log.LogMe;

import java.lang.reflect.Method;

import static ru.clevertec.check.aspects.AspectConstants.*;
import static ru.clevertec.check.service.CheckConstants.ONE_INT;
import static ru.clevertec.check.service.CheckConstants.ZERO_INT;

@Slf4j
@AllArgsConstructor
@Component
@Aspect
public class LogAspect {
    private final Gson gson;

    @Pointcut("execution(@ru.clevertec.check.annotations.log.* * *(..))")
    private void methodToBeProfiled() {
    }

    @SneakyThrows
    @AfterReturning(pointcut = "methodToBeProfiled()", returning = "o")
    public void createLog(JoinPoint joinPoint, Object o) {
        StringBuilder stringArgs = new StringBuilder();
        StringBuilder stringResult = new StringBuilder();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        if (method.getDeclaringClass().isInterface()) {
            method = joinPoint.getTarget().getClass()
                    .getDeclaredMethod(methodSignature.getName(), method.getParameterTypes());
        }
        String methodName = methodSignature.getName() + BRACKETS;
        Class returnType = methodSignature.getReturnType();
        String result = gson.toJson(o);
        Object[] args = joinPoint.getArgs();
        stringArgs.append(className)
                .append(SPACE)
                .append(methodName)
                .append(SPACE);
        stringResult.append(className)
                .append(SPACE)
                .append(methodName)
                .append(SPACE);
        if (args.length != ZERO_INT) {
            stringArgs.append(ARGS);
            for (int i = ZERO_INT; i < args.length; i++) {
                stringArgs.append(args[i].getClass().getSimpleName() + EQUALS);
                stringArgs.append(gson.toJson(args[i]));
                if (i < args.length - ONE_INT) {
                    stringArgs.append(COMMA);
                }
            }
            stringResult.append(RESULT).append(returnType.getSimpleName() + EQUALS)
                    .append(result);
        } else {
            stringArgs.append(WITHOUTARGS);
        }
        if (returnType.equals(StringBuilder.class)) {
            stringResult.append(STRINGBUILDER);
        } else if (returnType.equals(void.class)) {
            stringResult.append(NORETURN);
        }
        LogMe annotation = method.getAnnotation(LogMe.class);
        LogLevel level = annotation.value();
        switch (level) {
            case INFO -> {
                log.info(stringArgs.toString());
                log.info(stringResult.toString());
            }
            case DEBUG -> {
                log.debug(stringArgs.toString());
                log.debug(stringResult.toString());
            }
            case WARN -> {
                log.warn(stringArgs.toString());
                log.warn(stringResult.toString());
            }
            case TRACE -> {
                log.trace(stringArgs.toString());
                log.trace(stringResult.toString());
            }
            case ERROR -> {
                log.error(stringArgs.toString());
                log.error(stringResult.toString());
            }
        }
    }
}

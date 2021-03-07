package ru.clevertec.aop.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
//import ru.clevertec.check.annotations.log.LogMe;
//import ru.clevertec.check.annotations.log.LogLevel;
//import ru.clevertec.check.exception.ProductException;
//import ru.clevertec.check.utils.parser.JsonParser;
//import ru.clevertec.check.utils.parser.impl.JsonParserImpl;

@Aspect
public class LogAspect {
//    static Logger logger = LogManager.getLogger();
//    private static final String SPACE = " ";
//    private static final String DOT = ".";
//    private static final String ARGS = "Args: ";
//    private static final String COMMA = ", ";
//    private static final String RESULT = "Result: ";
//    private static final String RETURN = "Return ";
//    private static final String WITHOUTARGS = "Without Args";
//    private static final String STRINGBUILDER = "StringBuilder";
//    private static final String NORETURN = "Nothing to return";
//
//    @Pointcut("execution(@ru.clevertec.check.annotations.log.* * *(..))")
//    private void methodToBeProfiled() {
//    }
//
//    @AfterReturning(pointcut = "methodToBeProfiled()", returning = "o")
//    public void createLog(JoinPoint joinPoint, Object o) throws IllegalAccessException {
//        StringBuilder stringArgs = new StringBuilder();
//        StringBuilder stringResult = new StringBuilder();
//        JsonParser jsonParser = new JsonParserImpl();
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        String className = joinPoint.getTarget().getClass().toString();
//        String methodName = methodSignature.getName() + "()";
//        Class returnType = methodSignature.getReturnType();
//        String result = jsonParser.parseJson(o);
//        Object[] args = joinPoint.getArgs();
//        stringArgs.append(className)
//                .append(SPACE)
//                .append(methodName)
//                .append(SPACE);
//        stringResult.append(className)
//                .append(SPACE)
//                .append(methodName)
//                .append(SPACE);
//        if (args.length != 0) {
//            stringArgs.append(ARGS);
//            for (int i = 0; i < args.length; i++) {
//                stringArgs.append(args[i].getClass().getSimpleName() + "= ");
//                stringArgs.append(jsonParser.parseJson(args[i]));
//                if (i < args.length - 1) {
//                    stringArgs.append(COMMA);
//                }
//            }
//            stringResult.append(RESULT).append(returnType.getSimpleName() + "= ")
//                    .append(result);
//        } else {
//            stringArgs.append(WITHOUTARGS);
//        }
//
//        if (returnType.equals(StringBuilder.class)) {
//            stringResult.append(STRINGBUILDER);
//        } else if (returnType.equals(void.class)) {
//            stringResult.append(NORETURN);
//        }
//        LogMe annotation = (LogMe) methodSignature.getMethod().getAnnotation(LogMe.class);
//        LogLevel level = annotation.value();
//        switch (level) {
//            case INFO -> {
//                logger.info(stringArgs);
//                logger.info(stringResult);
//            }
//            case DEBUG -> {
//                logger.debug(stringArgs);
//                logger.debug(stringResult);
//            }
//            case WARN -> {
//                logger.warn(stringArgs);
//                logger.warn(stringResult);
//            }
//            case TRACE -> {
//                logger.trace(stringArgs);
//                logger.trace(stringResult);
//            }
//            case ERROR -> {
//                logger.error(stringArgs);
//                logger.error(stringResult);
//            }
//            case FATAL -> {
//                logger.fatal(stringArgs);
//                logger.fatal(stringResult);
//            }
//        }
//    }

//    @AfterThrowing(value = "methodToBeProfiled()", throwing = "e")
//    public void loginException(JoinPoint joinPoint, Exception e) {
//        logger.error(e);
//    }
}

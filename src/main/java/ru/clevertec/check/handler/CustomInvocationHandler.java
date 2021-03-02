package ru.clevertec.check.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CustomInvocationHandler implements InvocationHandler {
    private Object obj;
    static Logger logger = LogManager.getLogger();
    public CustomInvocationHandler(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        for(int i=0; i< args.length; i++){
            if(args[i].getClass().equals(StringBuilder.class)){
                stringBuilder.append("StringBuilder");
                stringBuilder.append(" ");
            }
            else if(args[i].getClass().equals(String[].class)){
                stringBuilder.append(args[i].getClass().getSimpleName());
                stringBuilder.append(" ");
            }
            else{
                stringBuilder.append(args[i]);
                stringBuilder.append(" ");
            }
        }
        if(method.getReturnType()!=void.class){
            logger.info("Completed  "+ method.getName() + "() method in "
                    + this.obj.getClass().getSimpleName() + " class with params" + stringBuilder.toString()+
                    ". Result: "+method.invoke(obj,args));
        }
        else{
            logger.info("Completed  "+ method.getName() + "() method in "
                    + this.obj.getClass().getSimpleName() + " class with params" + stringBuilder.toString());
        }
        return method.invoke(obj,args);
    }
}

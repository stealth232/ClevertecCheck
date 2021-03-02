package ru.clevertec.check.proxy;

import ru.clevertec.check.handler.CustomInvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory {

public static Object doProxy(Object obj){
    ClassLoader classLoader = obj.getClass().getClassLoader();
    Class classObj = obj.getClass();
    Class[] interfaces = obj.getClass().getInterfaces();
    Object proxyObj = (Object) Proxy.newProxyInstance(classLoader, interfaces, new CustomInvocationHandler(obj));
    return proxyObj;
    }
}

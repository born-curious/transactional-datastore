package com.transactional.proxy;

import com.transactional.annotation.Transactional;
import com.transactional.manager.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionalProxy implements InvocationHandler {

    private Object target;
    private TransactionManager transactionManager;

    public TransactionalProxy(Object target, TransactionManager transactionManager) {
        this.target = target;
        this.transactionManager = transactionManager;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Transactional.class)) {
            transactionManager.beginTransaction();
            try {
                Object object = method.invoke(target, args);
                transactionManager.commitTransaction();
                return object;
            } catch (Exception e) {
                transactionManager.rollbackTransaction();
                throw e;
            }
        } else {
            return method.invoke(target, args);
        }
    }

    public static Object createProxy(Object target, TransactionManager transactionManager) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new TransactionalProxy(target, transactionManager));
    }
}
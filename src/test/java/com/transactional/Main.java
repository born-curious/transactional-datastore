package com.transactional;

import com.transactional.manager.TransactionManager;
import com.transactional.proxy.TransactionalProxy;

public class Main {

    public static void main(String[] args) {
        TransactionManager transactionManager = TransactionManager.getInstance();
        TestClass testClass = new TestClass();
        TestInterface proxy = (TestInterface) TransactionalProxy.createProxy(testClass, transactionManager);
        String[] inputFalse = new String[]{"key1", "value1", "key2", "value2"};
        proxy.doSomething(false, inputFalse);
        System.out.println(proxy.getSomething(inputFalse[0]));
        System.out.println(proxy.getSomething(inputFalse[2]));

        String[] inputTrue = new String[]{"key3", "value3", "key4", "value4"};
        try {
            proxy.doSomething(true, inputTrue);
        } catch (Exception e) {
            System.out.println(proxy.getSomething(inputTrue[0]));
            System.out.println(proxy.getSomething(inputTrue[2]));
            System.out.println(proxy.getSomething(inputFalse[0]));
            System.out.println(proxy.getSomething(inputFalse[2]));
        }
    }
}
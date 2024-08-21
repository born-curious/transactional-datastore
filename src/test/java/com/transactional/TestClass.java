package com.transactional;

import com.transactional.annotation.Transactional;
import com.transactional.datastore.TransactionalMapDatastore;
import com.transactional.datastore.impl.TransactionalMapDatastoreImpl;

public class TestClass implements TestInterface {

    private TransactionalMapDatastore<String, String> map = new TransactionalMapDatastoreImpl<>();

    @Override
    @Transactional
    public void doSomething(boolean throwError, String... args) {
        map.put(args[0], args[1]);
        System.out.println("I am doing something here");
        map.put(args[2], args[3]);
        if(throwError) {
            throw new RuntimeException("Checking Rollback with Exception!!!");
        }
    }

    @Override
    public String getSomething(String key) {
        return map.get(key);
    }
}

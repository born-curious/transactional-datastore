package com.transactional.threadcontext;

import com.transactional.models.DatastoreTransaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.currentThread;

public class ThreadContextManager {

    private static ThreadContextManager instance = new ThreadContextManager();
    private Map<Long, DatastoreTransaction> threadContextMap;

    public static ThreadContextManager getInstance() {
        return instance;
    }

    public void put(DatastoreTransaction dt) {
        threadContextMap.put(currentThread().getId(), dt);
    }

    public DatastoreTransaction get() {
        return threadContextMap.get(currentThread().getId());
    }

    public void remove() {
        threadContextMap.remove(currentThread().getId());
    }

    private ThreadContextManager() {
        threadContextMap = new ConcurrentHashMap<>();
    }
}

package com.transactional.models;

import com.transactional.datastore.TransactionalDatastore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DatastoreTransaction {

    private final String dsTxnId;
    private final Map<Object, Set<TransactionalDatastore>>  transactionalDataMap;

    public DatastoreTransaction() {
        this(UUID.randomUUID().toString());
    }

    public DatastoreTransaction(String dsTxnId) {
        this.dsTxnId = dsTxnId;
        this.transactionalDataMap = new HashMap<>();
    }

    public void put(Object key, TransactionalDatastore td) {
        Set<TransactionalDatastore> tdSet = transactionalDataMap.getOrDefault(key, new HashSet<>());
        tdSet.add(td);
        transactionalDataMap.put(key, tdSet);
    }

    public Set<TransactionalDatastore> get(Object key) {
        return transactionalDataMap.get(key);
    }

    public Set<Object> keySet() {
        return transactionalDataMap.keySet();
    }
}


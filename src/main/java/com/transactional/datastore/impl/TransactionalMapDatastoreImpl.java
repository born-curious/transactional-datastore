package com.transactional.datastore.impl;

import com.transactional.datastore.TransactionalMapDatastore;
import com.transactional.manager.TransactionManager;

import java.util.concurrent.ConcurrentHashMap;

public class TransactionalMapDatastoreImpl<K, V> extends ConcurrentHashMap<K, V> implements
        TransactionalMapDatastore<K, V> {

    public TransactionalMapDatastoreImpl() {
        super();
    }

    @Override
    public V put(K key, V value) {
        TransactionManager.getInstance().addKey(key, this);
        return super.put(key, value);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        TransactionManager.getInstance().addKey(key, this);
        return super.putIfAbsent(key, value);
    }
}

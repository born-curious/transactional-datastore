package com.transactional.manager;

import com.transactional.datastore.TransactionalMapDatastore;
import com.transactional.datastore.TransactionalDatastore;
import com.transactional.models.DatastoreTransaction;
import com.transactional.threadcontext.ThreadContextManager;

import java.util.Objects;
import java.util.Set;

public class TransactionManager {

    private static TransactionManager instance = new TransactionManager();
    private ThreadContextManager threadContextManager;

    public static TransactionManager getInstance() {
        return instance;
    }

    public void beginTransaction() {
        DatastoreTransaction datastoreTransaction = new DatastoreTransaction();
        threadContextManager.put(datastoreTransaction);
    }

    public void commitTransaction() {
        removeDatastoreTransaction();
    }

    public void rollbackTransaction() {
        DatastoreTransaction datastoreTransaction = threadContextManager.get();
        if(Objects.nonNull(datastoreTransaction)) {
            for (Object key : datastoreTransaction.keySet()) {
                Set<TransactionalDatastore> datastores = datastoreTransaction.get(key);
                for (TransactionalDatastore datastore : datastores) {
                    if (datastore instanceof TransactionalMapDatastore<?, ?> transactionalMapDatastore) {
                        transactionalMapDatastore.remove(key);
                    }
                }
            }
        }
        removeDatastoreTransaction();
    }

    public void addKey(Object key, TransactionalDatastore td) {
        DatastoreTransaction datastoreTransaction = threadContextManager.get();
        if(Objects.nonNull(datastoreTransaction)) {
            datastoreTransaction.put(key, td);
        }
    }

    private void removeDatastoreTransaction() {
        threadContextManager.remove();
    }

    private TransactionManager() {
        threadContextManager = ThreadContextManager.getInstance();
    }
}

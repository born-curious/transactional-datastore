package com.transactional.datastore;

import java.util.concurrent.ConcurrentMap;

public interface TransactionalMapDatastore<K, V> extends ConcurrentMap<K, V>, TransactionalDatastore {

}

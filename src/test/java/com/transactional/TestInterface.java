package com.transactional;

import com.transactional.annotation.Transactional;

public interface TestInterface {

    void doSomething(boolean throwError, String... args);

    String getSomething(String key);
}

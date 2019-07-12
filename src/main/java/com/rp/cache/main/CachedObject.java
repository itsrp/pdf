package com.rp.cache.main;

public interface CachedObject {

    String getPassword();

    void setPassword(String password);

    int getFailedAttempts();

    void setFailedAttempts(int attempts);

}

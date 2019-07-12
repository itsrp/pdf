package com.rp.cache.main;

public interface Authenticator {
    boolean authenticate(String username, String password);
}

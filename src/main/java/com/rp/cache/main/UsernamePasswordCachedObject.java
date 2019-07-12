package com.rp.cache.main;

public class UsernamePasswordCachedObject implements CachedObject {

    private String password;

    private int failedAttempts;

    public UsernamePasswordCachedObject(String password) {
        this.password = password;
    }

    public UsernamePasswordCachedObject(String password, int failedAttempts) {
        this.password = password;
        this.failedAttempts = failedAttempts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    @Override
    public String toString() {
        return "UsernamePasswordCachedObject{" +
                "password='" + password + '\'' +
                ", failedAttempts=" + failedAttempts +
                '}';
    }
}

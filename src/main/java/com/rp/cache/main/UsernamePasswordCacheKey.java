package com.rp.cache.main;

public class UsernamePasswordCacheKey implements CacheKey {

    private String username;

    private String password;

    public UsernamePasswordCacheKey(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsernamePasswordCacheKey usernamePasswordCacheKey = (UsernamePasswordCacheKey) o;

        return username.equals(usernamePasswordCacheKey.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}

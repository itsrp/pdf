package com.rp.cache.main;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class UserCacheAuthenticator implements CacheAuthenticator<CacheKey,CachedObject> {

    private static final int CACHE_EXPIRE_DURATION = 10;
    private int maxTryLimit;
    private LoadingCache<CacheKey, CachedObject> cache;

    private Authenticator authenticator;

    public UserCacheAuthenticator(Authenticator authenticator, int maxTryLimit) {
        this.authenticator = authenticator;
        this.maxTryLimit = maxTryLimit;
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(CACHE_EXPIRE_DURATION, TimeUnit.MINUTES)
                .build(new CacheLoader<CacheKey, CachedObject>() {
                    @Override
                    public CachedObject load(CacheKey key) throws Exception {
                        System.out.println("Loading cache");
                        boolean isAuthenticated = actualAuthentication(key.getId(), key.getPassword());
                        return isAuthenticated ? createCachedObjectInstance(key.getPassword(), 0) : createCachedObjectInstance(null, -1);
                    }
                });
    }

    @Override
    public int getMaxTryLimit() {
        return maxTryLimit;
    }

    @Override
    public LoadingCache<CacheKey, CachedObject> getCache() {
        return this.cache;
    }

    @Override
    public CachedObject createCachedObjectInstance(String password, int failedAttempts) {
        return new UsernamePasswordCachedObject(password, failedAttempts);
    }

    @Override
    public boolean actualAuthentication(String username, String password) {
        System.out.println("Calling Auth service");
        try {
            return authenticator.authenticate(username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}

package com.rp.cache.main;

import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;


public interface CacheAuthenticator<K extends CacheKey, V extends CachedObject> {

    String EMPTY = "";

    int getMaxTryLimit();

    LoadingCache<K, V> getCache();

    V createCachedObjectInstance(String password, int failedAttempts);

    boolean actualAuthentication(String username, String password);

    default V getIfPresent(K k) {
        return getCache().getIfPresent(k);
    }

    default V get(K k) {
        try {
            return getCache().get(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    default boolean authenticate(K k) throws ExecutionException {
        AtomicReference<V> ar = null;
        synchronized (this) {
            ar = new AtomicReference<>(getCache().get(k));
        }

        if(ar.get().getFailedAttempts() == -1) {
            System.out.println("First time failed");
            //failed first time
            V v = ar.get();
            v.setFailedAttempts(1);
            ar.set(v);
            getCache().put(k, ar.get());
            return false;
        } else  {
            //System.out.println("Not first. Last time failed");
            //failed last time
            if(k.getPassword().equalsIgnoreCase(ar.get().getPassword())){
                System.out.println("Password matched");
                V v = ar.get();
                v.setFailedAttempts(0);
                ar.set(v);
                getCache().put(k, ar.get());
                return true;
            }
            if(ar.get().getFailedAttempts() > getMaxTryLimit()-1) {
                System.out.println("Limit reached");
                return false;
            }
            System.out.println("actualAuthentication");
            boolean b = actualAuthentication(k.getId(), k.getPassword());
            if(b) {
                System.out.println("actualAuthentication: true");
                V v = ar.get();
                v.setFailedAttempts(1);
                ar.set(v);
                getCache().put(k, ar.get());
                return true;
            } else {
                System.out.println("actualAuthentication: false");
                V v = ar.get();
                v.setFailedAttempts(ar.get().getFailedAttempts() + 1);
                ar.set(v);
                getCache().put(k, ar.get());
                return false;
            }
        } /*else {
            System.out.println("First time passed");
            return true;
        }*/

        /*V alreadyCachedObj = getIfPresent(k);
        if(alreadyCachedObj == null) {
            return getCache().get(k) != null || onAuthenticationFailure(k, createCachedObjectInstance(EMPTY));
        }
        return validate(k, alreadyCachedObj);*/
    }

    /*default boolean validate(K k, V v) {
            if (k.getPassword().equals(v.getPassword())) {
                return onAuthenticationSuccess(k, createCachedObjectInstance(k.getPassword()));
            }
            return revalidateFromAuthenticator(k, v);
    }

    default Boolean revalidateFromAuthenticator(K k, V v) {
        if (v.getFailedAttempts() == getMaxTryLimit()) {
            return false;
        }
        return actualAuthentication(k.getId(), k.getPassword()) ?
                onAuthenticationSuccess(k, createCachedObjectInstance(k.getPassword())) :
                onAuthenticationFailure(k, v);
    }*/

    default boolean onAuthenticationSuccess(K k, V v) {
        getCache().put(k, v);
        System.out.println("Failed attempts: " + v.getFailedAttempts());
        return true;
    }

    default boolean onAuthenticationFailure(K k, V v) {
        //v.setPassword(EMPTY);
        v.setFailedAttempts(v.getFailedAttempts() + 1);
        getCache().put(k, v);
        System.out.println("Failed attempts: " + v.getFailedAttempts());
        return false;
    }
}

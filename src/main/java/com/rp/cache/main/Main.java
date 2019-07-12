package com.rp.cache.main;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws ExecutionException {

        /*LoadingCache<String, UsernamePasswordCachedObject> userCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .recordStats()
                .build(new CacheLoader<String, UsernamePasswordCachedObject>() {
                    @Override
                    public UsernamePasswordCachedObject load(String key) throws Exception {
                        System.out.println("Loading cache");
                        Map<String, UsernamePasswordCachedObject> map = new HashMap<String, UsernamePasswordCachedObject>();
                        map.put("RP1", new UsernamePasswordCachedObject("123"));
                        map.put("RP2", new UsernamePasswordCachedObject("1234"));
                        return map.get(key);
                    }
                });

        UsernamePasswordCachedObject object = userCache.get("RP1");
        System.out.println(object);*/

        /*CacheAuthenticator cacheAuthenticator = new UserCacheAuthenticator();
        UsernamePasswordCacheKey key = new UsernamePasswordCacheKey("RP1", "123");
        boolean isAuthenticated = cacheAuthenticator.authenticate(key);
        System.out.println("isAuthenticated: " + isAuthenticated);

        key = new UsernamePasswordCacheKey("RP1", "1233");
        isAuthenticated = cacheAuthenticator.authenticate(key);
        System.out.println("isAuthenticated: " + isAuthenticated);

        key = new UsernamePasswordCacheKey("RP1", "123");
        isAuthenticated = cacheAuthenticator.authenticate(key);
        System.out.println("isAuthenticated: " + isAuthenticated);

        String password = "1234";
        for (int i = 0; i< 11; i++) {
            if(i == 7) {
                password = "123";//correct pwd
            } else {
                password = "1234";//incorrect pwd
            }
            key = new UsernamePasswordCacheKey("RP1", password);
            isAuthenticated = cacheAuthenticator.authenticate(key);
            System.out.println("isAuthenticated: " + isAuthenticated);
            System.out.println("");
        }*/
    }
}

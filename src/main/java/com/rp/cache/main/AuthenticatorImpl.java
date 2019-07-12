package com.rp.cache.main;

import java.util.HashMap;
import java.util.Map;

public class AuthenticatorImpl implements Authenticator {
    @Override
    public boolean authenticate(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("RP1", "123");
        map.put("RP2", "1234");
        map.put("username", "valid-password");
        String tempPwd = map.get(username);
        return tempPwd != null && tempPwd.equals(password);
    }
}

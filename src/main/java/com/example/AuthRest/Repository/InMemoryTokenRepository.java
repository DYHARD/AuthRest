package com.example.AuthRest.Repository;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTokenRepository {
    private final ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<>(); // token -> email

    public void save(String token, String email) {
        tokenStore.put(token, email);
    }

    public boolean exists(String token) {
        return tokenStore.containsKey(token);
    }

    public void revoke(String token) {
        tokenStore.remove(token);
    }
}

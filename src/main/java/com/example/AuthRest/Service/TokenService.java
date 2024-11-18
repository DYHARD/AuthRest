package com.example.AuthRest.Service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final ConcurrentHashMap<String, Long> revokedTokens = new ConcurrentHashMap<>();

    public void revokeToken(String token, long expirationTime) {
        revokedTokens.put(token, expirationTime);
    }

    public boolean isTokenRevoked(String token) {
        Long expiry = revokedTokens.get(token);
        if (expiry == null) {
            return false;
        }
        if (System.currentTimeMillis() > expiry) {
            revokedTokens.remove(token);
            return false;
        }
        return true;
    }
}

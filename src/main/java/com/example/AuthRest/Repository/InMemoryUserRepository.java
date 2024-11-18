package com.example.AuthRest.Repository;

import com.example.AuthRest.Model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository {

    private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();
    public User save(User user) {
        Long id = idCounter.incrementAndGet();
        user.setId(id);
        users.put(user.getEmail(), user);
        return user;
    }


    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }
}

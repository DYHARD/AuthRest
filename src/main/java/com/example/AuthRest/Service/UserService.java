package com.example.AuthRest.Service;

import com.example.AuthRest.Model.User;
import com.example.AuthRest.Repository.InMemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private InMemoryUserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signUp(String email, String password){
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    public boolean validateCredentials(String email, String password) {
//        String hashedPassword = userRepository.findPasswordByEmail(email);
//        return hashedPassword != null && passwordEncoder.matches(password, hashedPassword);
//    }
    
}

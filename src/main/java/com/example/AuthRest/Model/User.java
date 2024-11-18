package com.example.AuthRest.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter
public class User {

    private Long id;
    private String email;
    private String password; // Hashed password

    public User() {}

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}

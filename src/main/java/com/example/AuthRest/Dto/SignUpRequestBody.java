package com.example.AuthRest.Dto;

import lombok.Data;
import lombok.Getter;

@Data
public class SignUpRequestBody {
    private String email;
    private String password;
}

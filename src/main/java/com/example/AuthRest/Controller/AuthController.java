package com.example.AuthRest.Controller;

import com.example.AuthRest.Component.JwtGenerator;
import com.example.AuthRest.Dto.SignUpRequestBody;
import com.example.AuthRest.Model.User;
import com.example.AuthRest.Service.TokenService;
import com.example.AuthRest.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    private TokenService tokenService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestBody payload){
        String email = payload.getEmail();
        String password = payload.getPassword();

        userService.signUp(email, password);

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<String> signIn(@RequestBody Map<String, String> payload){

        String email = payload.get("email");
        String password = payload.get("password");

        Optional<User> user = userService.findByEmail(email);

        if(user.isPresent() && new BCryptPasswordEncoder().matches(password, user.get().getPassword())){
            String token = ""; // generate Token here
            jwtGenerator.generateToken(email);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        try {
            String parsedToken = token.replace("Bearer ", "");
            Claims claims = jwtGenerator.validateToken(parsedToken);
            String email = claims.getSubject();
            String newToken = jwtGenerator.generateToken(email);
            tokenService.revokeToken(parsedToken, claims.getExpiration().getTime());
            return ResponseEntity.ok(Collections.singletonMap("token", newToken));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Token expired"));
        }
    }

    @PostMapping("/revoke")
    public ResponseEntity<?> revokeToken(@RequestHeader("Authorization") String token) {
        try {
            String parsedToken = token.replace("Bearer ", "");
            Claims claims = jwtGenerator.validateToken(parsedToken);
            tokenService.revokeToken(parsedToken, claims.getExpiration().getTime());
            return ResponseEntity.ok(Collections.singletonMap("message", "Token revoked successfully"));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Token already expired"));
        }
    }


}

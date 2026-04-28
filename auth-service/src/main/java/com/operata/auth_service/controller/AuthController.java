package com.operata.auth_service.controller;

import com.operata.auth_service.dto.LoginRequest;
import com.operata.auth_service.dto.RegisterRequest;
import com.operata.auth_service.service.AuthService;
import com.operata.auth_service.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            String response = authService.registerUser(registerRequest);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try{
            String token = authService.loginUser(loginRequest);
            return ResponseEntity.ok(token);

        }catch (Exception ex){
            return ResponseEntity.status(401).body(ex.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.isTokenValid(token)) {
                String email = jwtService.extractEmail(token);
                return ResponseEntity.ok(email);
            }
        }

        return ResponseEntity.status(401).body("Invalid Token");
    }
}

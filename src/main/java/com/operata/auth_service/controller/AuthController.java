package com.operata.auth_service.controller;

import com.operata.auth_service.dto.LoginRequest;
import com.operata.auth_service.dto.RegisterRequest;
import com.operata.auth_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private  final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
}

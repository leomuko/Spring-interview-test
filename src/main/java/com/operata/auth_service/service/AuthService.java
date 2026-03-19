package com.operata.auth_service.service;


import com.operata.auth_service.dto.RegisterRequest;
import com.operata.auth_service.entity.User;
import com.operata.auth_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.findByPhoneNumber(registerRequest.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already exists");
        }

        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPhoneNumber(registerRequest.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(newUser);

        return "User registered successfully!";
    }
}

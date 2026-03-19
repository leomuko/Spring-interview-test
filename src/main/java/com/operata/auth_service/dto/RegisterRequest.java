package com.operata.auth_service.dto;

public class RegisterRequest {
    private String phoneNumber;
    private String email;
    private String password;

    // Getters and Setters
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

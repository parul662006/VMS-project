package com.example.myproject.model;


import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {

    @NotBlank(message="email is required")
    private String email;
    @NotBlank(message="password is required")
    private String password;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

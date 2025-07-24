package com.example.myproject.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String name;
    private String email;
    private String role;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

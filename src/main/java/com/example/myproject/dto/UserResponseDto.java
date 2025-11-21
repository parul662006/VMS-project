package com.example.myproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {
    private int id;
    private String name;
    private String role;
    private LocalDateTime created_at;
}

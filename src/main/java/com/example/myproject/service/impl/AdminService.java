package com.example.myproject.service.impl;

import com.example.myproject.model.UserRequestDto;
import com.example.myproject.model.UserResponseDto;

public interface AdminService {

    // for admin only
    UserResponseDto registerAdmin(UserRequestDto userDto);
    String loginAdmin(String email, String password);
}

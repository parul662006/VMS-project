package com.example.myproject.service;

import com.example.myproject.dto.LoginResponseDto;
import com.example.myproject.dto.UserRequestDto;
import com.example.myproject.dto.UserResponseDto;

public interface UserService {

    //for users only
    UserResponseDto createUser(UserRequestDto userDto);
    UserResponseDto getUserById(int id);
    String loginUser(String email, String password);
    LoginResponseDto getUserByName(String name);


    // for admin only
    UserResponseDto registerAdmin(UserRequestDto userDto);
    String loginAdmin(String email, String password);
}

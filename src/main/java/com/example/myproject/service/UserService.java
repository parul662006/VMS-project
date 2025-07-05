package com.example.myproject.service;

import com.example.myproject.model.LoginResponseDto;
import com.example.myproject.model.UserRequestDto;
import com.example.myproject.model.UserResponseDto;

public interface UserService {

    //for users only
    UserResponseDto createUser(UserRequestDto userDto);
    UserResponseDto getUserById(int id);
    String loginUser(String email, String password);
    LoginResponseDto getUserByName(String name);
}

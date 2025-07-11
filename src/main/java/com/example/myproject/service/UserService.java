package com.example.myproject.service;

import com.example.myproject.dto.LoginResponseDto;
import com.example.myproject.dto.UserRequestDto;
import com.example.myproject.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    //for users only
    UserResponseDto createUser(UserRequestDto userDto);
    UserResponseDto getUserById(int id);
    LoginResponseDto loginUser(String email, String password);
    LoginResponseDto getUserByName(String name);


    // for admin only
    UserResponseDto registerAdmin(UserRequestDto userDto);

    //get all data
    List<LoginResponseDto> getAllData();
}

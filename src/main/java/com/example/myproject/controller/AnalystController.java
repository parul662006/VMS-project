package com.example.myproject.controller;

import com.example.myproject.model.*;
import com.example.myproject.service.CveService;
import com.example.myproject.service.UserService;
import com.example.myproject.service.impl.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnalystController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;



    // post user data
    @PostMapping("/post-user-data")
    public ResponseEntity<?> postData(@Valid @RequestBody UserRequestDto userDto){
        UserResponseDto dto=userService.createUser(userDto);
        return ResponseEntity.ok(dto);
    }


    //  Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/user/name/{name}")
    public ResponseEntity<LoginResponseDto> getUserByname(@PathVariable String name) {
        LoginResponseDto user = userService.getUserByName(name);
        return ResponseEntity.ok(user);
    }

    //POST ADMIN DATA
    @PostMapping("/post-admin-data")
    public ResponseEntity<?> postAdminData(@Valid @RequestBody UserRequestDto userDto){
        UserResponseDto dto= adminService.registerAdmin(userDto);
        return ResponseEntity.ok(dto);
    }

    //login Admin and user
    @PostMapping("/login-page")
    public String adminData(@Valid @RequestBody LoginRequestDto dto){
        return  userService.loginUser(dto.getEmail(), dto.getPassword());
    }



}

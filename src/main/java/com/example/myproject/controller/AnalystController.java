package com.example.myproject.controller;

import com.example.myproject.dto.LoginRequestDto;
import com.example.myproject.dto.LoginResponseDto;
import com.example.myproject.dto.UserRequestDto;
import com.example.myproject.dto.UserResponseDto;
import com.example.myproject.response.APIResponse;
import com.example.myproject.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnalystController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;




    // post user data
    @PostMapping("/post-user-data")
    public ResponseEntity<?> postData(@Valid @RequestBody UserRequestDto userDto){
        UserResponseDto dto=userService.createUser(userDto);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "User data upload successfully", dto);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }


    //  Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    //POST ADMIN DATA
    @PostMapping("/post-admin-data")
    public ResponseEntity<?> postAdminData(@Valid @RequestBody UserRequestDto userDto){
        UserResponseDto dto= userService.registerAdmin(userDto);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Admin data upload successfully", dto);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }

    //login Admin and user
    @PostMapping("/login-page")
    public ResponseEntity<APIResponse<LoginResponseDto>> adminData(@Valid @RequestBody LoginRequestDto dto){
        LoginResponseDto loginResponseDto= userService.loginUser(dto.getEmail(), dto.getPassword());
        String message="Hii"+" "+loginResponseDto.getName()+" "+loginResponseDto.getRole()+", you're login successfully with your email "+loginResponseDto.getEmail();
        APIResponse<LoginResponseDto> apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), message, loginResponseDto);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    // delete users by their id
    @DeleteMapping("delete-data-by-id/{id}")
    public ResponseEntity<String> deleteData(@PathVariable int id){
        return ResponseEntity.ok("data delected successfully of id : "+id);
    }

    @GetMapping("get-All-users-data")
    public ResponseEntity<List<LoginResponseDto>> getAllData(){
        return ResponseEntity.ok(userService.getAllData());
    }


}

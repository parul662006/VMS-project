package com.example.myproject.service.impl;

import com.example.myproject.enumCode.EnumProgram;
import com.example.myproject.globalException.EmailAlreadyExistsException;
import com.example.myproject.globalException.UserNotFoundException;
import com.example.myproject.model.*;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.UserService;
import com.example.myproject.utility.UtilProgram;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto){

        //REGISTER FOR USER ONLY
        // Step 1: Check if email already exists
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + userRequestDto.getEmail());
        }

        //convert dto -> entity
        Analyst analyst=modelMapper.map(userRequestDto, Analyst.class);
        UtilProgram.matchPassword(userRequestDto.getPassword(),userRequestDto.getConfirm_password());

        //set role
        analyst.setRole(EnumProgram.Role.USER);

        //save user in db
        Analyst saveUser=userRepository.save(analyst);

        //convert entity -> response dto
        return modelMapper.map(saveUser, UserResponseDto.class);

    }


    //for get user by id
    @Override
    public UserResponseDto getUserById(int id) {
        Analyst analyst = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found with this id :" + id));
        return modelMapper.map(analyst, UserResponseDto.class);
    }

    //check login email and password entered by user
    @Override
    public String loginUser(String email, String password){
        Analyst analyst=userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("user not found with this email :"+email));

        //check password
        UtilProgram.checkPassword(password,analyst.getPassword());

        //check name which user entered
        //UtilProgram.validateNameMatch(name, analyst.getName());
        LoginResponseDto loginResponseDto=new LoginResponseDto();
        loginResponseDto.setName(analyst.getName());


        //return to response dto
       // LoginResponseDto ll=  modelMapper.map(analyst, LoginResponseDto.class);

        //return success mesaage
        return "Hi! "+loginResponseDto.getName()+" "+analyst.getRole()+" login successfully with your email"+" "+analyst.getEmail();
    }

    //getting user data by name
    @Override
    public LoginResponseDto getUserByName(String name){
        Analyst analyst=userRepository.findByName(name)
                .orElseThrow(()->new RuntimeException("User not found with this name"+" "+name));
        return modelMapper.map(analyst, LoginResponseDto.class);
    }




}

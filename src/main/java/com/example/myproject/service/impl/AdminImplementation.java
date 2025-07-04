package com.example.myproject.service.impl;

import com.example.myproject.enumCode.EnumProgram;
import com.example.myproject.globalException.EmailAlreadyExistsException;
import com.example.myproject.globalException.UserNotFoundException;
import com.example.myproject.model.Analyst;
import com.example.myproject.model.LoginResponseDto;
import com.example.myproject.model.UserRequestDto;
import com.example.myproject.model.UserResponseDto;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.utility.UtilProgram;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminImplementation implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    //REGISTER ONLY FOR ADMINS HERE
    @Override
    public UserResponseDto registerAdmin(UserRequestDto userRequestDto){

        // Step 1: Check if email already exists
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + userRequestDto.getEmail());
        }

        //convert dto -> entity
        Analyst analyst=modelMapper.map(userRequestDto, Analyst.class);
        UtilProgram.matchPassword(userRequestDto.getPassword(),userRequestDto.getConfirm_password());

        //set role
        analyst.setRole(EnumProgram.Role.ADMIN);

        //save user in db
        Analyst saveUser=userRepository.save(analyst);

        //convert entity -> response dto
        return modelMapper.map(saveUser, UserResponseDto.class);

    }
    //check login email and password entered by user
    @Override
    public String loginAdmin(String email, String password){
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

}

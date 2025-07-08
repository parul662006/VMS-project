package com.example.myproject.utility;

import com.example.myproject.globalException.InvalidCredentialsException;
import com.example.myproject.globalException.InvalidPasswordException;

public class UtilProgram {
    public static void checkPassword(String password,String actualPassword){
        if(!password.equals(actualPassword)){
            throw new InvalidCredentialsException("Password incorrect");
        }
    }

    public static void matchPassword(String password,String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException("password doesn't match");
        }
    }



}

package com.example.myproject.utility;

import com.example.myproject.globalException.MyCustomException;

public class UtilProgram {
    public static void checkPassword(String password,String actualPassword){
        if(!password.equals(actualPassword)){
            throw new MyCustomException.InvalidCredentialsException("Password incorrect");
        }
    }

    public static void matchPassword(String password,String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new MyCustomException.InvalidPasswordException("password doesn't match");
        }
    }



}

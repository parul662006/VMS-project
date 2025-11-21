package com.example.myproject.globalException;

public class MyCustomException {
    public static class EmailAlreadyExistsException extends RuntimeException{
        public EmailAlreadyExistsException(String message){
            super(message);
        }
    }

    public static class InvalidCredentialsException extends RuntimeException{
        public InvalidCredentialsException(String message){
            super(message);
        }
    }

    public static class InvalidPasswordException extends RuntimeException{
        public InvalidPasswordException(String message){
            super(message);
        }
    }
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message){
            super(message);
        }
    }

}

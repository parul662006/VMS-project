package com.example.myproject.globalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException ex) {

            List<String> errors = ex.getBindingResult() // give errors information
                    .getFieldErrors()//give field level error like name ,email
                    .stream()//convert list -> stream so that can perform map
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());//stream -> list

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        //UserNotFoundException global handler
        @ExceptionHandler(MyCustomException.UserNotFoundException.class)
         public ResponseEntity<String> userNotFound(MyCustomException.UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user not found");
        }

        //InvalidCredentialsException global handler
        @ExceptionHandler(MyCustomException.InvalidCredentialsException.class)
        public ResponseEntity<String> invalidCredentials(MyCustomException.InvalidCredentialsException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("your credentials are invalid");
        }

        //InvalidPasswordException global handler
        @ExceptionHandler(MyCustomException.InvalidPasswordException.class)
        public ResponseEntity<String> invalidCredentials(MyCustomException.InvalidPasswordException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("your password doesn't match please try again !!");
        }

        // EmailAlreadyExistsExceptionglobal handler
        @ExceptionHandler(MyCustomException.EmailAlreadyExistsException.class)
        public ResponseEntity<String> invalidEmailCredentials( MyCustomException.EmailAlreadyExistsException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is already exists in your database !!");
        }


}



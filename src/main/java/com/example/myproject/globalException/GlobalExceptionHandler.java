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
        @ExceptionHandler(UserNotFoundException.class)
         public ResponseEntity<String> userNotFound(UserNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This user not found");
        }

        //InvalidCredentialsException global handler
        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<String> invalidCredentials(InvalidCredentialsException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("your credentials are invalid");
        }

        //InvalidPasswordException global handler
        @ExceptionHandler(InvalidPasswordException.class)
        public ResponseEntity<String> invalidCredentials(InvalidPasswordException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("your password doesn't match please try again !!");
        }

        // EmailAlreadyExistsExceptionglobal handler
        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<String> invalidEmailCredentials( EmailAlreadyExistsException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is already exists in your database !!");
        }


}



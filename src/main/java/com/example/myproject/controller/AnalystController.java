package com.example.myproject.controller;

import com.example.myproject.dto.LoginRequestDto;
import com.example.myproject.dto.LoginResponseDto;
import com.example.myproject.dto.UserRequestDto;
import com.example.myproject.dto.UserResponseDto;
import com.example.myproject.model.Analyst;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.response.APIResponse;
import com.example.myproject.service.UserService;
import com.example.myproject.utility.TokenUtilProgram;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "User APIs", description = "Operations related to User data")
@RestController
@RequestMapping("/api/auth")
public class AnalystController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenUtilProgram tokenUtilProgram;



    // post user data
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user and sends a verification token (valid for 10 minutes) to the provided email."
    )

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Data uploaded successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping("/post-user-data")
    public ResponseEntity<?> postData(@Valid @RequestBody UserRequestDto userDto){
        UserResponseDto dto=userService.createUser(userDto);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "User data upload successfully", dto);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }


    //  Get user by ID
    @Operation(
            summary = "Get user by ID",
            description = "Get user by its ID"
    )

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "user data fetched successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    //POST ADMIN DATA
    @Operation(
            summary = "Register a new admin",
            description = "Registers a new admin user in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin data uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/post-admin-data")
    public ResponseEntity<?> postAdminData(@Valid @RequestBody UserRequestDto userDto){
        UserResponseDto dto= userService.registerAdmin(userDto);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Admin data upload successfully", dto);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }

    //login Admin and user
    @Operation(
            summary = "Login admin or user",
            description = "Login as admin or user using email and password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login-page")
    public ResponseEntity<APIResponse<LoginResponseDto>> adminData(@Valid @RequestBody LoginRequestDto dto){
        LoginResponseDto loginResponseDto= userService.loginUser(dto.getEmail(), dto.getPassword());
        String message="Hii"+" "+loginResponseDto.getName()+" "+loginResponseDto.getRole()+", you're login successfully with your email "+loginResponseDto.getEmail();
        APIResponse<LoginResponseDto> apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), message, loginResponseDto);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    // delete users by their id
    @Operation(
            summary = "Delete user by ID",
            description = "Deletes a user by their unique ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("delete-data-by-id/{id}")
    public ResponseEntity<String> deleteData(@PathVariable int id){
        userService.deleteDataById(id);
        return ResponseEntity.ok("data delected successfully of id : "+id);
    }

    //Delete all
    @Operation(
            summary = "Delete all users",
            description = "Deletes all users from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All user data deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("delete-all-user-data")
    public ResponseEntity<String> deleteAll(UserRequestDto userRequestDto){
        userService.deleteAllData(userRequestDto);
        return ResponseEntity.ok("all data deleted successfully");
    }

    // get all
    @Operation(
            summary = "Get all users",
            description = "Fetches all registered users' data"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("get-All-users-data")
    public ResponseEntity<List<LoginResponseDto>> getAllData(){
        return ResponseEntity.ok(userService.getAllData());
    }

    //data update by patch
    @Operation(
            summary = "Partially update user by ID",
            description = "Updates specific fields of a user by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("user/update/{id}")
    public ResponseEntity<APIResponse<Analyst>> updateUserDataPartially(@PathVariable int id,@RequestBody Analyst updatedUser){
        Analyst analyst=userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("user not found of this id :"+" "+id));
        if(updatedUser.getName()!= null){
            analyst.setName(updatedUser.getName());
        }if(updatedUser.getEmail()!=null){
            analyst.setEmail(updatedUser.getEmail());
        }if(updatedUser.getRole()!=null){
            analyst.setRole(updatedUser.getRole());
        }if(updatedUser.getPhone_no()!=null){
            analyst.setPhone_no(updatedUser.getPhone_no());
        }if(updatedUser.getPassword()!=null){
            analyst.setPassword(updatedUser.getPassword());
        }
        //save
        userRepository.save(analyst);
        APIResponse<Analyst> apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "User data updated successfully of this id"+""+id, analyst);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    //email
    @Operation(
            summary = "Verify email with token",
            description = "Verifies email using a token sent via email"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/verify")
    public ResponseEntity<String> verifiyEmail(@RequestParam String token){
        String result=tokenUtilProgram.verifyEmailByToken(token);
        return ResponseEntity.ok(result);
    }


}

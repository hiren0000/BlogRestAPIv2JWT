package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.config.AppiConsta;
import com.rebel.BlogAPIv2.enitities.UserRole;
import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.UserDto;
import com.rebel.BlogAPIv2.repo.UserRoleRepo;
import com.rebel.BlogAPIv2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/users/")
@CrossOrigin("*")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepo userRoleRepo;

    //Post creating and also we are using Validation annotation to perform all the required validations
    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto)
    {
        //USER must be normal user through the Registration
        Set<UserRole> roles = new HashSet<>();

        roles.add(this.userRoleRepo.findById(AppiConsta.NORMAL_USER).get());

        UserDto createdUser = this.userService.createUser(userDto, roles);
        System.out.println(createdUser.getName());

        HttpStatus status = HttpStatus.CREATED;
        String message = "User has been added into db !! ";

        Map<String, Object> map = Map.of("user", userDto, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }


    //Getting OTP for verification only afterwards users will be enabled
    @PostMapping("/otp-verification")
    public ResponseEntity<?> getOtpForVerify(@PathVariable Integer id, @PathVariable Long otp)
    {
        String otpMsg = this.userService.getOtp(id, otp);

        HttpStatus status = HttpStatus.OK;
        String message = otpMsg;
        System.out.println(message);

        Map<String, Object> map = Map.of( "Status", status, "message", message);

        return ResponseEntity.ok(map);

    }


    //Putting updating I am only updating limited variables from user service pls make sure u can do more
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id)
    {
       UserDto updatedDto =  this.userService.updatingUser(userDto, id);
        System.out.println("Update method in use");

        HttpStatus status = HttpStatus.OK;
        String message = "User has been updated successfully !! ";

        Map<String, Object> map = Map.of("user", updatedDto, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //getting the list of users
    @GetMapping("/")
    public ResponseEntity<?> getALlUsers(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize)
    {
        List<UserDto> list = this.userService.getAllUser(pageNumber, pageSize);

        HttpStatus status = HttpStatus.OK;
        String message = "User are getting fetched from db !! ";

        Map<String, Object> map = Map.of("user", list, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //getting user by userId
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByuId(@PathVariable Integer id)
    {
        UserDto existingUser = this.userService.getUserById(id);
        System.out.println("Fetching single user data");

        HttpStatus status = HttpStatus.OK;
        String message = "User are getting fetched from db !! ";

        Map<String, Object> map = Map.of("user", existingUser, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //removing user by id from the Data base
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletebyId(@PathVariable Integer id)
    {
        this.userService.deleteUserById(id);

        HttpStatus status = HttpStatus.OK;
        String message = "User has been deleted from db !! ";

        Map<String, Object> map = Map.of("Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

}

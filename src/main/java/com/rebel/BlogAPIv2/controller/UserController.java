package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.UserDto;
import com.rebel.BlogAPIv2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private UserService userService;

    //Post creating
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
    {
        UserDto createdUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    //Putting updating
    @PutMapping("/{uId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer uId)
    {
       UserDto updatedDto =  this.userService.updatingUser(userDto, uId);

       return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    //getting the list of users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getALlUsers()
    {
        List<UserDto> list = this.userService.getAllUser();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //getting user by userId
    @GetMapping("/{uId}")
    public ResponseEntity<UserDto> getUserByuId(@PathVariable Integer uId)
    {
        UserDto existingUser = this.userService.getUserById(uId);

        return new ResponseEntity<>(existingUser, HttpStatus.FOUND);
    }

    //removing user by id from the Data base
    @DeleteMapping("/{uId}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable Integer uId)
    {
        this.userService.deleteUserById(uId);

        return new ResponseEntity(new ApiResponse("user is successfully deleted", true), HttpStatus.OK);
    }

}

package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.UserDto;
import com.rebel.BlogAPIv2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users/")
public class UserController
{
    @Autowired
    private UserService userService;

    //Post creating and also we are using Validation annotation to perform all the required validations
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUser = this.userService.createUser(userDto);
        System.out.println(createdUser.getName());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    //Putting updating
    @PutMapping("/{uId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer uId)
    {
       UserDto updatedDto =  this.userService.updatingUser(userDto, uId);

       return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    //getting the list of users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getALlUsers(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize)
    {
        List<UserDto> list = this.userService.getAllUser(pageNumber, pageSize);

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

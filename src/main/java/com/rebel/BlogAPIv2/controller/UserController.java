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
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        //USER must be normal user through the Registration
        Set<UserRole> roles = new HashSet<>();

        roles.add(this.userRoleRepo.findById(AppiConsta.NORMAL_USER).get());

        UserDto createdUser = this.userService.createUser(userDto, roles);
        System.out.println(createdUser.getName());

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    //Putting updating I am only updating limited variables from user service pls make sure u can do more
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id)
    {
       UserDto updatedDto =  this.userService.updatingUser(userDto, id);
        System.out.println("Update method in use");
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
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserByuId(@PathVariable Integer id)
    {
        UserDto existingUser = this.userService.getUserById(id);
        System.out.println("Fetching single user data");
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

    //removing user by id from the Data base
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable Integer id)
    {
        this.userService.deleteUserById(id);

        return new ResponseEntity(new ApiResponse("user is successfully deleted", true), HttpStatus.OK);
    }

}

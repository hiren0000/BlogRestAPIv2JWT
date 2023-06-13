package com.rebel.BlogAPIv2.services;

import com.rebel.BlogAPIv2.payloads.UserDto;

import java.util.List;


public interface UserService
{
      //creating new user
      UserDto createUser(UserDto userDto);

      //get user by id
     UserDto getUserById(Integer uId);

     //get all users
     List<UserDto> getAllUser(Integer pageNumber, Integer pageSize);

     //update user
     UserDto updatingUser(UserDto userDto, Integer uId);

     //delete user
     void deleteUserById(Integer uId);



}

package com.rebel.BlogAPIv2.services;

import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.enitities.UserRole;
import com.rebel.BlogAPIv2.payloads.UserDto;

import java.util.List;
import java.util.Set;


public interface UserService
{
      //creating new user
      UserDto createUser(UserDto userDto, Set<UserRole> userRoles);

      //get user by id
     UserDto getUserById(Integer uId);

     //get all users
     List<UserDto> getAllUser(Integer pageNumber, Integer pageSize);

     //update user
     UserDto updatingUser(UserDto userDto, Integer uId);

     //delete user
     void deleteUserById(Integer uId);

     //Getting user ID and OTP to verify here and update the user Profile
     String getOtp(Integer uId, Long otp);

     //Getting user by Otp
     UserDto getUserByOtp(Long otp);

     //Registration confirmation email
    void sendRegistrationConfirmationEmail(User user);



}

package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.config.AppiConsta;
import com.rebel.BlogAPIv2.enitities.User;
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


    @GetMapping("/registration/verification/")
    public ResponseEntity<?> verifyRegistration(@RequestParam String token)
    {


        if(token.isEmpty())
        {
            return ResponseEntity.ok("token is Empty !!");
        }
        try
        {
             userService.verifyUser(token);

        }catch (Exception e)
        {
            System.out.println("something wrong with verification registration !!");
        }

        return ResponseEntity.ok("Email verification done !!");
    }


    //Getting OTP for verification only afterwards users will be enabled
    @PostMapping("/otp-verification/{otp}")
    public ResponseEntity<?> getOtpForVerify(@PathVariable Long otp)
    {
        UserDto userDto = this.userService.getUserByOtp(otp);

        Map<String, Object> map;

        if(userDto == null)
        {
            HttpStatus status = HttpStatus.NOT_FOUND;
            String message = "User does not exist !!";
            System.out.println(message);

            map = Map.of("user", userDto, "Status", status, "message", message);

            return ResponseEntity.ok(map);
        }
        else
        {
            //Http status and message as usual
            HttpStatus status = HttpStatus.OK;
            String message = "User has successfully activate their account !!";

            map = Map.of("user", userDto, "Status", status, "message", message);

            return ResponseEntity.ok(map);
        }

    }



    //this function will helpful for forget password
    @PostMapping("/forget-password/{email}")
    public ResponseEntity<?> getEmailForVerify(@PathVariable String email) {

        Map<String, Object> map;

        UserDto userDto = this.userService.getUserByEmail(email);

        if(userDto == null)
        {
            HttpStatus status = HttpStatus.NOT_FOUND;
            String message = "User does not exist !!";

            map = Map.of("user", userDto,"Status", status, "message", message);
            return ResponseEntity.ok(map);

        }
        else
        {
            //Http status and message as usual
            HttpStatus status = HttpStatus.OK;
            String message = "Email is on the way with OTP to reset password !!";

            map = Map.of("user", userDto, "Status", status, "message", message);

            return ResponseEntity.ok(map);
        }

    }

    //forget password function, helping to communicate with db and verify user is inputting accurate OTP
    @PostMapping("/otp-verification/forget-pass/{otp}")
    public ResponseEntity<?> verifyForForgetPass(@PathVariable Long otp)
    {
        UserDto userDto = this.userService.getUserByOtp(otp);

        Map<String, Object> map;

        if(userDto == null)
        {
            HttpStatus status = HttpStatus.NOT_FOUND;
            String message = "User does not exist !!";
            System.out.println(message);

            map = Map.of("user", userDto, "Status", status, "message", message);

            return ResponseEntity.ok(map);
        }
        else
        {
            //Http status and message as usual
            HttpStatus status = HttpStatus.OK;
            String message = "User is in DB, letting them Reset the password !!";

            map = Map.of("user", userDto, "Status", status, "message", message);

            return ResponseEntity.ok(map);
        }

    }



    //Updating user password for forget pass function only
    @PutMapping("/forget-password/resetting-password/{id}")
    public ResponseEntity<?> updateUserPassword(
            @RequestBody UserDto userDto, @PathVariable Integer id)
    {
        UserDto updatedDto =  this.userService.updatingUser(userDto, id);
        System.out.println("Update method in use");

        Map<String, Object> map;

        if(updatedDto == null)
        {
            HttpStatus status = HttpStatus.NOT_FOUND;
            String message = "User does not exist !! ";

            map = Map.of("user", updatedDto, "Status", status, "message", message);

            return ResponseEntity.ok(map);
        }
        else
        {
            HttpStatus status = HttpStatus.OK;
            String message = "User's password has been updated successfully !! ";

            map = Map.of("user", updatedDto, "Status", status, "message", message);

            return ResponseEntity.ok(map);
        }

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

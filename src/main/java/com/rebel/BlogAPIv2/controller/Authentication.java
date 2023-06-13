package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.jwtExtra.JwtRequest;
import com.rebel.BlogAPIv2.jwtExtra.JwtResponse;
import com.rebel.BlogAPIv2.jwtExtra.JwtUtils;
import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.LoginDto;
import com.rebel.BlogAPIv2.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/auth/")
@CrossOrigin("*")
public class Authentication
{

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private CustomUserDetailService customUserDetailService;

        @Autowired
        private JwtUtils jwtUtil;

        //generate token

        @PostMapping("/generate-token")
        public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception
        {

            try
            {
                authenticate(jwtRequest.getEmail(), jwtRequest.getPass());

                System.out.println(jwtRequest.getEmail());
            }
            catch (UsernameNotFoundException e)
            {
                e.printStackTrace();
                throw new Exception("User not found ");
            }

            //authenticated user

            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtRequest.getEmail());
            String token = this.jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));

        }




        private void authenticate(String username, String password) throws Exception
        {
            try
            {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            }
            catch (DisabledException e)
            {
                throw new DisabledException(" USER IS DISABLED ");
            }
            catch(BadCredentialsException e)
            {
                throw new Exception("Invalid Credentials "+ e);
            }

        }


        // returning the details of current user
        @GetMapping("/current-user")
        public User getCurrentUser(Principal principal)
        {
            return (User)this.customUserDetailService.loadUserByUsername(principal.getName());
        }

}

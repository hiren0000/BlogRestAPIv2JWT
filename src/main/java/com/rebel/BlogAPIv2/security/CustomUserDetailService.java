package com.rebel.BlogAPIv2.security;

import com.rebel.BlogAPIv2.enitities.User;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService
{
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.repo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "email"+ " "+ username, 0));


        return user;

    }
}

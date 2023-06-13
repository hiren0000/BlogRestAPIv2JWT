package com.rebel.BlogAPIv2.config;

import com.rebel.BlogAPIv2.jwtExtra.JwtUtils;
import com.rebel.BlogAPIv2.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilterCustom extends OncePerRequestFilter
{

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {

        // Header::  THis is filter and run always before calling an API so intially will give null values
        final String requestTokenHeader = request.getHeader("Authorization");
        System.out.println(requestTokenHeader);

        String username = null;
        String jwtToken = null;

        // Token Part -1
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
        {
            //condition-true
            try
            {
                jwtToken = requestTokenHeader.substring(7);

                username = this.jwtUtil.extractUsername(jwtToken);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("something wrong with with Token part -1   ");
            }

        }
        else
        {
            System.out.println("Invalid token, or might not start with Bearer string Token part-1");
        }

        //------------------------------------------------------------------------------------------------------

        //Validating Token (Token part -2 )
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            final UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);

            if(this.jwtUtil.validateToken(jwtToken, userDetails))
            {
                //after validating token

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));



                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        else
        {
            System.out.println("Token is not valid !! ");
        }

        filterChain.doFilter(request,response);

    }
}

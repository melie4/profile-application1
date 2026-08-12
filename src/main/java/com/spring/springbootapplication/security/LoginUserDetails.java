package com.spring.springbootapplication.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Collection;

import com.spring.springbootapplication.entity.User;


public class LoginUserDetails extends org.springframework.security.core.userdetails.User { 
   
    private com.spring.springbootapplication.entity.User user;

    public LoginUserDetails(com.spring.springbootapplication.entity.User user,Collection<GrantedAuthority> authorities){
        super(
            user.getEmail(), 
            user.getPassword(), 
            authorities
        );

        this.user = user;
    }

    public com.spring.springbootapplication.entity.User getUser(){
        return user;
    }   
    
}

package com.spring.springbootapplication.security;

import org.springframework.security.core.GrantedAuthority;
import java.util.List;

import com.spring.springbootapplication.entity.User;


public class LoginUser extends org.springframework.security.core.userdetails.User { 
   
    private com.spring.springbootapplication.entity.User user;

    public LoginUser(com.spring.springbootapplication.entity.User user){
        super(
            user.getEmail(), 
            user.getPassword(), 
            List.of()
        );

        this.user = user;
    }

    public com.spring.springbootapplication.entity.User getUser(){
        return user;
    }   
    
}

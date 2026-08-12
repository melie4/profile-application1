package com.spring.springbootapplication.service;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.security.LoginUserDetails;
import com.spring.springbootapplication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class LoginUserDetailsService implements UserDetailsService{
    @Autowired
    UserRepository repository;

    @Override
    public UserDetails  loadUserByUsername(String email) throws UsernameNotFoundException{
       
        User user = repository.findByEmail(email);
       

        if(user == null){
            throw new UsernameNotFoundException("メールアドレスが見つかりません：" + email);

        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        /*authorities.add(new SimpleGrantedAuthority(""));*/
        return new LoginUserDetails(user,authorities);
    }
    
}

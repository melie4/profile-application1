package com.spring.springbootapplication.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //データベースからUserの一覧取得
    public List<User> findAll(){
        return repository.findAll();
    }

    //データベースにユーザー登録
    public void userRegister(User user){
        if(repository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("このメールアドレスは既に登録されています");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

         LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdateAt(now);

        
        repository.saveAndFlush(user);
    }
}

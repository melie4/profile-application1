package com.spring.springbootapplication.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.repository.UserRepository;
import com.spring.springbootapplication.dto.ProfileEditDto;
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

    //自己紹介編集
    public void updateProfile(
        User loginUserInfo,
        MultipartFile avatarImage,
        ProfileEditDto formUser
        )
        throws IOException{

         //自己紹介文の設定
        loginUserInfo.setSelfIntroduction(
        formUser.getSelfIntroduction()
        );

        if(!avatarImage.isEmpty()){
          
        //画像の設定
        String originalFileName = avatarImage.getOriginalFilename();

        String fileName =
            UUID.randomUUID() + "-" + originalFileName;

        Path uploadDir = Paths.get("profile-images");
        Files.createDirectories(uploadDir);

        Path imagePath = uploadDir.resolve(fileName);

        Files.copy(
            avatarImage.getInputStream(),
            imagePath,
            StandardCopyOption.REPLACE_EXISTING
        );

        loginUserInfo.setAvatarImage(fileName);
        }

        LocalDateTime now = LocalDateTime.now();
        loginUserInfo.setUpdateAt(now);

        repository.saveAndFlush(loginUserInfo);

        }
}

package com.spring.springbootapplication.controller;

import com.spring.springbootapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;

import com.spring.springbootapplication.dto.ProfileEditDto;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.security.LoginUserDetails;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class ProfileEditController {
    
    private final UserService userService;

    ProfileEditController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/profile-edit")
    public ModelAndView index(
        @ModelAttribute("userModel") ProfileEditDto form,
        @AuthenticationPrincipal LoginUserDetails loginUser,
        ModelAndView mav,
        HttpServletRequest request){
            mav.setViewName("profile-edit");
            
            //DBに登録済みの自己紹介文を取得する
            User loginUserInfo = loginUser.getUser();
            form.setSelfIntroduction(
            loginUserInfo.getSelfIntroduction()
            );


           return mav;
    
    }

    @RequestMapping(value = "/profile-edit", method= RequestMethod.POST)
    public ModelAndView form(
        @ModelAttribute("userModel") @Validated ProfileEditDto formUser,
        BindingResult result,
        @RequestParam("avatarImage") MultipartFile avatarImage,
        @AuthenticationPrincipal LoginUserDetails loginUser,
        HttpServletRequest request,
        ModelAndView mav) throws IOException{
            
        if(result.hasErrors()){
        mav.setViewName("profile-edit");
       
        return mav;
        }

        User loginUserInfo = loginUser.getUser();

        userService.updateProfile(loginUserInfo,avatarImage,formUser);
    
        return new ModelAndView("redirect:/top");
    
    }
    

}

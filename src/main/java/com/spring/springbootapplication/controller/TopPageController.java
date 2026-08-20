package com.spring.springbootapplication.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.security.LoginUserDetails;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TopPageController {
    @RequestMapping("/top")
    public ModelAndView index(
            ModelAndView mav, 
            HttpServletRequest request,
            @AuthenticationPrincipal LoginUserDetails loginUser) {
                
        mav.setViewName("topLoggedIn");
        String userName = request.getRemoteUser();
        mav.addObject("userName", userName + "  portfolio site");

        //ログイン中のユーザーのLoginUserDetailsを渡す
        User user = loginUser.getUser();
        mav.addObject("user",user);

        return mav;
    }
}

package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class LoginController {
    
    @RequestMapping("/login")
    public ModelAndView index(ModelAndView mav) {
       mav.setViewName("login");
       mav.addObject("title","ログイン");

       return mav;
    }
    
}

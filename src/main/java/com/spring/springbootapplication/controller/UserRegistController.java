package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.BindingResult;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.spring.springbootapplication.security.LoginUser;

import com.spring.springbootapplication.repository.UserRepository;
import com.spring.springbootapplication.service.UserService;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.dto.UserRegisterDto;

import jakarta.transaction.Transactional;


@Controller
public class UserRegistController {
    
    @Autowired
    UserRepository repository;

    @Autowired
    UserService service;

    @RequestMapping("/")
    public ModelAndView index(
        @ModelAttribute("userModel") User user,
        ModelAndView mav){
      mav.setViewName("signIn");
      mav.addObject("title", "新規登録");
      
      return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Transactional
    public ModelAndView form(
        @ModelAttribute("userModel") @Validated UserRegisterDto dto,
        BindingResult result,
        ModelAndView mav){

        if(result.hasErrors()){
        mav.setViewName("signIn");
        mav.addObject("title", "新規登録");
        return mav;
    }

      User user  = new User();
      user.setName(dto.getName());
      user.setEmail(dto.getEmail());
      user.setPassword(dto.getPassword());

      service.userRegister(user);

      LoginUser loginUser = new LoginUser(user);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
            loginUser,
            null,
            loginUser.getAuthorities());
      
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return new ModelAndView("redirect:/top");
    }

    @RequestMapping("/top")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("topLoggedIn");
        return mav;
    }
    

}

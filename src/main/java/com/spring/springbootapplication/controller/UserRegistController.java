package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;


@Controller
public class UserRegistController {
    
    @Autowired
    UserRepository repository;

    @Autowired
    UserService service;

    @Autowired
    SecurityContextRepository securityContextRepository;

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
        ModelAndView mav,
        HttpServletRequest request,
        HttpServletResponse response){

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
      
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authentication);

      SecurityContextHolder.setContext(context);

      //認証情報をセッションに保存
      securityContextRepository.saveContext(context, request, response);
    
      return new ModelAndView("redirect:/top");
    }

    @RequestMapping("/top")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("topLoggedIn");
        return mav;
    }
    

}

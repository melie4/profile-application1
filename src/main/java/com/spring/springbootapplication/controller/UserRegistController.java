package com.spring.springbootapplication.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.BindingResult;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.spring.springbootapplication.security.LoginUserDetails;

import com.spring.springbootapplication.repository.UserRepository;
import com.spring.springbootapplication.service.UserService;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.dto.UserRegisterDto;

import jakarta.transaction.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.Collection;



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
    public ModelAndView form(
        @ModelAttribute("userModel") @Validated UserRegisterDto dto,
        BindingResult result,
        ModelAndView mav,
        HttpServletRequest request,
        HttpServletResponse response){

        //バリデーションチェック時のエラーの表示
        if(result.hasErrors()){
        mav.setViewName("signIn");
        mav.addObject("title", "新規登録");
        return mav;
    }

      User user  = new User();
      user.setName(dto.getName());
      user.setEmail(dto.getEmail());
      user.setPassword(dto.getPassword());

      //登録処理中のエラーの表示
      try {service.userRegister(user);}
      catch (IllegalArgumentException e) {
        mav.setViewName("signIn");
        mav.addObject("title", "新規登録");
        mav.addObject("errorMessage", e.getMessage());
        return mav;
        }
      //権限付与
      Collection<GrantedAuthority> authorities = new ArrayList<>();
      LoginUserDetails loginUser = new LoginUserDetails(user,authorities);

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

}    
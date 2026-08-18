package com.spring.springbootapplication.security;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.support.SessionFlashMapManager;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;


@Component
public class LoginFailureHandler implements AuthenticationFailureHandler{
    private final FlashMapManager flashMapManager;

    public LoginFailureHandler() {
        this.flashMapManager = new SessionFlashMapManager();
    }

    
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException {

        FlashMap flashMap = new FlashMap();

        // Flashにエラーメッセージを保存
        flashMap.put(
            "errorMessage",
            "メールアドレス、もしくはパスワードが間違っています"
        );
    
        flashMapManager.saveOutputFlashMap(
            flashMap,
            request,
            response
        );
        
         response.sendRedirect("/login");
    }
}


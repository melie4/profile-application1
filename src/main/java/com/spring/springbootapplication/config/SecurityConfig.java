package com.spring.springbootapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception{
            
            http.authorizeHttpRequests(authorize -> 
                authorize
                    .requestMatchers("/**").permitAll()
                    .requestMatchers("/js/**").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/img/**").permitAll()
                    .anyRequest().authenticated()
            );
            
            
            return http.build();
        }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }    

    
}

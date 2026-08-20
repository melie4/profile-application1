package com.spring.springbootapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.spring.springbootapplication.security.LoginFailureHandler;
import com.spring.springbootapplication.service.LoginUserDetailsService;


@Configuration
public class SecurityConfig {
    private final LoginFailureHandler failureHandler;
    private final LoginUserDetailsService userDetailsService;

    public SecurityConfig(LoginFailureHandler failureHandler,
                        LoginUserDetailsService userDetailsService){
        this.failureHandler = failureHandler; 
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception{
            
            http.authorizeHttpRequests(authorize -> 
                authorize
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/js/**").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/img/**").permitAll()
                    .requestMatchers("/profile-images/**").permitAll()
                    .anyRequest().authenticated()
            )
            .userDetailsService(userDetailsService)
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureHandler(failureHandler)
                .defaultSuccessUrl("/top",true)
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login")
            );
        
            
            
            return http.build();
        }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }    

    @Bean
    public SecurityContextRepository securityContextRepository() {
    return new HttpSessionSecurityContextRepository();
}

    
}

package com.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
         http.csrf().disable().cors().disable();
         http.authorizeRequests().anyRequest().permitAll();
       return   http.build();
    }
}

package com.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTRequestFilter jwtRequestFilter;

    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
         http.csrf().disable().cors().disable();
         http.addFilterBefore(jwtRequestFilter ,AuthorizationFilter.class);
         http.authorizeHttpRequests()
                 .requestMatchers("/api/v1/users/signUp"
                         ,"/api/v1/users/login" ,"/api/v1/users/token"
                         ,"/api/countries/addCountry"
                         , "/api/v1/users/profile"
                         , "/api/v1/users/profile/{id}")
                 .permitAll()
                 .requestMatchers("/api/v1/countries/addCountry").hasRole("ADMIN")
                 .requestMatchers("/api/v1/users/profile").hasAnyRole("ADMIN" , "USER")
                 .anyRequest().authenticated();
       return   http.build();
    }
}

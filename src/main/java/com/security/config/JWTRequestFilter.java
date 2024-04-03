package com.security.config;

import com.security.Service.JWTService;
import com.security.entity.PropertyUser;
import com.security.repository.PropertyUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;


@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private PropertyUserRepository  propertyUserRepository;

    public JWTRequestFilter(JWTService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }

    // cheak the token is valid or not this methods
    // check the token is valid or not
    // if the token is valid then proceed to the next filter
    // if the token is not valid then return 401 Unauthorized
    // if the token is null then return 401 Unauthorized
    // if the token is empty then return 401 Unauthorized
    // if the token is invalid then return 401 Unauthorized

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(8, tokenHeader.length()-1);
            String username = jwtService.getUsername(token);
            Optional<PropertyUser> opUser = propertyUserRepository.findByUsername(username);
            if(opUser.isPresent()){
                PropertyUser user = opUser.get();
                // for server to understand who is thw current user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user , null , Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole())));
               authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);
    }
}

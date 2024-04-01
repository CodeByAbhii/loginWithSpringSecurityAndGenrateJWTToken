package com.security.Service;

import com.security.entity.PropertyUser;
import com.security.payload.LoginDto;
import com.security.payload.PropertyUserDto;
import com.security.repository.PropertyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PropertyUserRepository propertyUserRepository;

    private JWTService jwtService;

    public UserService(PropertyUserRepository propertyUserRepository, JWTService jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService = jwtService;
    }


    public PropertyUser addUser(PropertyUserDto dto){

        PropertyUser user = new PropertyUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        // 1st way-->  to encrpt password this is Not Highly Secure for security perpase
 //       user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
       // 2nd way--> to encrpt password this is Very Secure for security perpase

        user.setPassword(BCrypt.hashpw(dto.getPassword() ,BCrypt.gensalt(10)) );

        user.setUserRole(dto.getUserRole());
        propertyUserRepository.save(user);
        return user;

    }

    public Boolean verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> optionalUser = propertyUserRepository.findByUsername(loginDto.getUsername());
        if (optionalUser.isPresent()) {
               PropertyUser user = optionalUser.get();
                BCrypt.checkpw(loginDto.getPassword(), user.getPassword());
            }
            return false;

    }

    public String verifyLogins(LoginDto loginDto) {
        Optional<PropertyUser> optionalUser = propertyUserRepository.findByUsername(loginDto.getUsername());
        if (optionalUser.isPresent()) {
            PropertyUser user = optionalUser.get();
           if( BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
               return jwtService.generateToken(user);
           }
        }
        return null;

    }
}

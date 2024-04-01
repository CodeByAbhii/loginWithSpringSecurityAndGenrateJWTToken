package com.security.Contoller;


import com.security.Service.UserService;
import com.security.entity.PropertyUser;
import com.security.payload.JWTResponse;
import com.security.payload.LoginDto;
import com.security.payload.PropertyUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    // http://localhost:8080/api/v1/users/signUp

    @PostMapping("/signUp")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto dto){
        PropertyUser user = userService.addUser(dto);
        if(user != null){
            return new ResponseEntity<>("User Sign Up Successfully" , HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went Wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // http://localhost:8080/api/v1/users/login


    // verify login in the database
    @PostMapping("/login")
    public ResponseEntity<String> verifyLogin(@RequestBody LoginDto loginDto){
        Boolean status = userService.verifyLogin(loginDto);
        if(status){
           return new ResponseEntity<>("User signed Successfully" , HttpStatus.OK);
        }
      return new ResponseEntity<>("Invalid Credentials" , HttpStatus.UNAUTHORIZED);
    }



    // http://localhost:8080/api/v1/users/token

    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String jwtToken = userService.verifyLogins(loginDto);
        if(jwtToken!=null){
            JWTResponse jwtResponse = new JWTResponse();
            jwtResponse.setToken(jwtToken);
            return new ResponseEntity<>(jwtResponse , HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Credentials" , HttpStatus.UNAUTHORIZED);
    }


}

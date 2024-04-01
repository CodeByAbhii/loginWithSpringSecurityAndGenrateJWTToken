package com.security.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.security.entity.PropertyUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {


    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry.Time}")
    private int expiryTime;
    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct(){
      algorithm =  Algorithm.HMAC256(secretKey);
    }

    public String generateToken(PropertyUser user){
        // sort cut off Genrating tokens - Computer Engineer are UnEmployee
     return    JWT.create()
                .withArrayClaim("USER_NAME", new String[]{user.getUsername()})
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

}

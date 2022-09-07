package com.example.goodsmanager.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthService {

    @Value("${jwt.config.secret}")
    private String secret;  // 密钥

    public String createToken(String username) {
        String token;
        Algorithm algorithm = Algorithm.HMAC256(secret);
        token = JWT.create()
                .withClaim("username", username)
                .sign(algorithm);
        return token;
    }

    public String verify(String token) throws JWTVerificationException {  // 返回username
        String username;
        Algorithm algorithm = Algorithm.HMAC256(secret); //use more secure key
        JWTVerifier verifier = JWT.require(algorithm)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        username = jwt.getClaim("username").asString();
        return username;
    }
}

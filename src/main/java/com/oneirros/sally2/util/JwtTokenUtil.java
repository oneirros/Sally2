package com.oneirros.sally2.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oneirros.sally2.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Component
public class JwtTokenUtil {

    public Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("longsecret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("SallyApp")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, String.format("Coś nie tak z tokenem: %s", e.getMessage()));
        }
    }

    //Zwraca Id na podstawie token-a
    public String getUserEmail(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    //Zwraca token reprezentujacy uzytkownika
    public String createToken(UserEntity user) {

        Algorithm algorithm = Algorithm.HMAC256("longsecret".getBytes());
        long currentTimeMillis = System.currentTimeMillis();
        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuer("SallyApp")
                .withIssuedAt(new Date(currentTimeMillis))
                .withExpiresAt(new Date(currentTimeMillis + 600000))
                .withClaim("role", user.getRole().getRoleName())
                .sign(algorithm);
    }
}

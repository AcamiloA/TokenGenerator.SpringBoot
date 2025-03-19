package com.andresalarcon.tokengenerator.infrastructure.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import com.andresalarcon.tokengenerator.application.dto.jwtResponses.JWTSuccessResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    
    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtProperties.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public Object generateToken(String username) {

        String token = Jwts.builder()
                .setHeaderParam("typ", jwtProperties.getType()) 
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new JWTSuccessResponse(HttpStatusCode.valueOf(200), "success", "Autenticación correcta", token, (jwtProperties.getExpiration() / 1000));
    }
}

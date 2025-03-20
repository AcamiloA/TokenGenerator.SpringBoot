package com.andresalarcon.tokengenerator.infrastructure.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.andresalarcon.tokengenerator.application.dto.LoginRequest;
import com.andresalarcon.tokengenerator.application.dto.bearerResponses.BearerSuccessResponse;
import com.andresalarcon.tokengenerator.application.dto.jwtResponses.JWTFailedResponse;
import com.andresalarcon.tokengenerator.application.service.IAuthService;
import com.andresalarcon.tokengenerator.domain.SecurityUtils;
import com.andresalarcon.tokengenerator.infrastructure.repository.InMemoryDatabase;
import com.andresalarcon.tokengenerator.infrastructure.security.BearerUtil;
import com.andresalarcon.tokengenerator.infrastructure.security.JwtUtil;

@Service
public class AuthService implements IAuthService {

    private final JwtUtil jwtUtil;
    private final BearerUtil bearerUtil;
    private final InMemoryDatabase database = InMemoryDatabase.getInstance();

    public AuthService(JwtUtil jwtUtil, BearerUtil bearerUtil){
        this.jwtUtil = jwtUtil;
        this.bearerUtil = bearerUtil;
    }

    @Override
    public Object authenticateByJWT(LoginRequest request) {

       return database.findUserByEmail(request.getUsername())
                .filter(user -> {
                    String hashedInputPassword = SecurityUtils.hashPassword(request.getPassword(), user.getSalt());
                    return user.getPasswordHash().equals(hashedInputPassword);
                })
                .map(user -> jwtUtil.generateToken(user.getEmail()))
                .orElseGet(() -> new JWTFailedResponse(HttpStatus.UNAUTHORIZED, "failed", "Credenciales incorrectas", request));
    
    }

    @Override
    public Object authenticateByBearer() {
        return new BearerSuccessResponse(HttpStatusCode.valueOf(200), "success", "Token generado con éxito", bearerUtil.generateBearer());
    }
}

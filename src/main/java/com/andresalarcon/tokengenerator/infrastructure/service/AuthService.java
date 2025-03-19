package com.andresalarcon.tokengenerator.infrastructure.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.andresalarcon.tokengenerator.application.dto.LoginRequest;
import com.andresalarcon.tokengenerator.application.dto.jwtResponses.JWTFailedResponse;
import com.andresalarcon.tokengenerator.application.service.IAuthService;
import com.andresalarcon.tokengenerator.infrastructure.security.JwtUtil;

@Service
public class AuthService implements IAuthService {

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Object authenticateByJWT(LoginRequest request) {

        if ("admin".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            return jwtUtil.generateToken(request.getUsername());
        }

        return new JWTFailedResponse(HttpStatusCode.valueOf(401), "failed", "Credenciales incorrectas", request);
    }
}

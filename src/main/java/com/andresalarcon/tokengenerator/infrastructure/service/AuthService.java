package com.andresalarcon.tokengenerator.infrastructure.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.andresalarcon.tokengenerator.application.dto.LoginRequest;
import com.andresalarcon.tokengenerator.application.dto.bearerResponses.BearerSuccessResponse;
import com.andresalarcon.tokengenerator.application.dto.jwtResponses.JWTFailedResponse;
import com.andresalarcon.tokengenerator.application.service.IAuthService;
import com.andresalarcon.tokengenerator.infrastructure.security.BearerUtil;
import com.andresalarcon.tokengenerator.infrastructure.security.JwtUtil;

@Service
public class AuthService implements IAuthService {

    private final JwtUtil jwtUtil;
    private final BearerUtil bearerUtil;

    public AuthService(JwtUtil jwtUtil, BearerUtil bearerUtil){
        this.jwtUtil = jwtUtil;
        this.bearerUtil = bearerUtil;
    }

    @Override
    public Object authenticateByJWT(LoginRequest request) {

        if ("admin".equals(request.getUsername()) && "password".equals(request.getPassword())) 
            return jwtUtil.generateToken(request.getUsername());        

        return new JWTFailedResponse(HttpStatusCode.valueOf(401), "failed", "Credenciales incorrectas", request);
    }

    @Override
    public Object authenticateByBearer() {
        return new BearerSuccessResponse(HttpStatusCode.valueOf(200), "success", "Token generado con éxito", bearerUtil.generateBearer());
    }
}

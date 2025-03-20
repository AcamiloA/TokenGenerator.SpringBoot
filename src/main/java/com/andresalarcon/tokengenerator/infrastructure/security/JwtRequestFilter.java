package com.andresalarcon.tokengenerator.infrastructure.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain chain) 
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("[DEBUG] Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("[DEBUG] No Authorization header or incorrect format.");
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("[DEBUG] Extracted Token: " + token);

        try {
            Claims claims = jwtUtil.validateToken(token);
            String email = claims.getSubject();
            System.out.println("[DEBUG] Token válido para usuario: " + email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            System.out.println("[ERROR] Token expirado.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado");
            return;
        } catch (JwtException e) {
            System.out.println("[ERROR] Token inválido: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return;
        }

        chain.doFilter(request, response);
    }
}

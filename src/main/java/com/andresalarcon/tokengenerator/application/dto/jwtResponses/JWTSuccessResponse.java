package com.andresalarcon.tokengenerator.application.dto.jwtResponses;

import org.springframework.http.HttpStatusCode;

import io.swagger.v3.oas.annotations.media.Schema;

public class JWTSuccessResponse {

    @Schema(description = "Código de State de la autenticación: 200", example = "200")
    private HttpStatusCode statusCode;   

    @Schema(description = "State de la autenticación: exitoso ", example = "success")
    private String state;   

    @Schema(description = "Message de la respuesta", example = "Autenticación correcta")
    private String message;

    @Schema(description = "Token JWT generado", example = "eyJhbGciOiJIUz...")    
    private String token;

    @Schema(description = "Tiempo de expiración del token (solo si el State es exitoso)", example = "3600")    
    private Integer expirationTime;

    public JWTSuccessResponse(HttpStatusCode statusCode, String state, String message, String token,
            Integer expirationTime) {
        this.statusCode = statusCode;
        this.state = state;
        this.message = message;
        this.token = token;
        this.expirationTime = expirationTime;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }
   
}

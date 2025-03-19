package com.andresalarcon.tokengenerator.application.dto.jwtResponses;

import org.springframework.http.HttpStatusCode;

import io.swagger.v3.oas.annotations.media.Schema;

public class JWTFailedResponse {
    @Schema(description = "Código de State de la autenticación: 401", example = "401")
    private HttpStatusCode statusCode;

    @Schema(description = "State de la autenticación fallido", example = "failed")
    private String state;

    @Schema(description = "Message de la respuesta", example = "Usuario o contraseña incorrrecto")
    private String message;

    @Schema(description = "JSON de la peticion", example = "{username: \"test\", password: \"passwordtest\"}") 
    private Object data;

    public JWTFailedResponse(HttpStatusCode statusCode, String state, String message, Object data) {
        this.statusCode = statusCode;
        this.state = state;
        this.message = message;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }   

}

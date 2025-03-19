package com.andresalarcon.tokengenerator.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andresalarcon.tokengenerator.application.dto.LoginRequest;
import com.andresalarcon.tokengenerator.application.dto.jwtResponses.JWTFailedResponse;
import com.andresalarcon.tokengenerator.application.dto.jwtResponses.JWTSuccessResponse;
import com.andresalarcon.tokengenerator.application.service.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints relacionados con autenticación")
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }


    @Operation(summary = "Generar token JWT", description = "Genera un token para autenticación tipo JWT")
    @PostMapping("/tokenJWT")
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = JWTSuccessResponse.class)))
    @ApiResponse(responseCode = "401", description = "Autenticación fallida", content = @Content(schema = @Schema(implementation = JWTFailedResponse.class)))
    public ResponseEntity<?> generateToken(@RequestBody LoginRequest credentials) {

        Object response = authService.authenticateByJWT(credentials);
        if (response instanceof JWTFailedResponse ) {
            return ResponseEntity.status(401).body(response);
        }
        return ResponseEntity.ok(response);
    }
    
    
}

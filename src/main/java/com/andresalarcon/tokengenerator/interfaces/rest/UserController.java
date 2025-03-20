package com.andresalarcon.tokengenerator.interfaces.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andresalarcon.tokengenerator.application.dto.UserDto;
import com.andresalarcon.tokengenerator.application.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar un usuario", description = "Registra un nuevo usuario con una contraseña segura.")
    public ResponseEntity<?> registerUser(@RequestBody UserDto user) {
        try{
            userService.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPasswordHash());
            return ResponseEntity.accepted().build();
        }catch(RuntimeException ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{email}")
    @Operation(summary = "Buscar usuario por email", description = "Obtiene la información de un usuario por su email.")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        String authenticatedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authenticatedEmail.equals(email)) {
            return ResponseEntity.status(403).build();
        }

        Optional<UserDto> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
   
    }

    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve la lista de todos los usuarios registrados.")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userList = userService.getAllUsers().stream()
        .map(user -> new UserDto(user.getEmail(), user.getFirstName(), user.getLastName(), "secret")).collect(Collectors.toList());

        if(!userList.isEmpty())
            return ResponseEntity.ok(userList);
        return ResponseEntity.noContent().build();

    }
    
}

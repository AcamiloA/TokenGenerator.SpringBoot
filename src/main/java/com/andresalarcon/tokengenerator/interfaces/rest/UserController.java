package com.andresalarcon.tokengenerator.interfaces.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andresalarcon.tokengenerator.application.dto.UserDto;
import com.andresalarcon.tokengenerator.application.service.IUserService;
import com.andresalarcon.tokengenerator.domain.model.User;

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
            User newUser = userService.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPasswordHash());
            URI location = URI.create("/users/" + newUser.getId());
            return ResponseEntity.created(location).body(newUser);
        }catch(RuntimeException ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{email}")
    @Operation(summary = "Buscar usuario por email", description = "Obtiene la información de un usuario por su email.")
    public ResponseEntity<Optional<UserDto>> getUserByEmail(@PathVariable String email) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        Optional<UserDto> user = userService.findUserByEmail(email);
        if(!user.isEmpty())
            return ResponseEntity.ok(user);
        return ResponseEntity.noContent().build();
   
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

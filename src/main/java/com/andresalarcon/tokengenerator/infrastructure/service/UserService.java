package com.andresalarcon.tokengenerator.infrastructure.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.andresalarcon.tokengenerator.application.dto.UserDto;
import com.andresalarcon.tokengenerator.application.service.IUserService;
import com.andresalarcon.tokengenerator.domain.SecurityUtils;
import com.andresalarcon.tokengenerator.domain.model.User;
import com.andresalarcon.tokengenerator.infrastructure.repository.InMemoryDatabase;

@Service
public class UserService implements IUserService{

    private final InMemoryDatabase database = InMemoryDatabase.getInstance();

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) {
        if (database.findUserByEmail(email).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        String salt = SecurityUtils.generateSalt();
        String passwordHash = SecurityUtils.hashPassword(password, salt);

        User newUser = new User(firstName, lastName, email, passwordHash, salt);
        database.addUser(newUser);
        return newUser;
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        return database.findUserByEmail(email).map(user -> new UserDto(user.getEmail(), user.getFirstName(), user.getLastName(), "secret"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return database.getAllUsers().stream()
                .map(user -> new UserDto(user.getEmail(), user.getFirstName(), user.getLastName(), "secret")).collect(Collectors.toList());
    }
    
}

package com.andresalarcon.tokengenerator.application.service;

import java.util.List;
import java.util.Optional;

import com.andresalarcon.tokengenerator.application.dto.UserDto;
import com.andresalarcon.tokengenerator.domain.model.User;

public interface IUserService {
    
    public User registerUser(String firstName, String lastName, String email, String password);
    public Optional<UserDto> findUserByEmail(String email);
    public List<UserDto> getAllUsers();
}

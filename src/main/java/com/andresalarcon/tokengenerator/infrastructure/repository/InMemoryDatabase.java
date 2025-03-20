package com.andresalarcon.tokengenerator.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.andresalarcon.tokengenerator.domain.model.User;

public class InMemoryDatabase {
    private static InMemoryDatabase instance;
    private final List<User> users = new ArrayList<>();

    private InMemoryDatabase() {}

    public static synchronized InMemoryDatabase getInstance() {
        if (instance == null) instance = new InMemoryDatabase();
                
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    public List<User> getAllUsers() {
        return users;
    }
}

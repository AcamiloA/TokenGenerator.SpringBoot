package com.andresalarcon.tokengenerator.domain.model;

public class User {
    private static long idCounter = 1; 
    private final long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String salt;

    public User(String firstName, String lastName, String email, String passwordHash, String salt) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public static long getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(long idCounter) {
        User.idCounter = idCounter;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
}

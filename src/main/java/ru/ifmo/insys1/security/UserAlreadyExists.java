package ru.ifmo.insys1.security;

public class UserAlreadyExists extends RuntimeException {

    public UserAlreadyExists(String username) {
        super(username);
    }
}

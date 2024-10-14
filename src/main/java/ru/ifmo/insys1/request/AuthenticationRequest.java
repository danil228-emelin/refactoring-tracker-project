package ru.ifmo.insys1.request;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;

    private String password;
}

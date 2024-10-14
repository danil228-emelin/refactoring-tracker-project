package ru.ifmo.insys1.response;

import lombok.Data;

@Data
public class RegistrationResponse {

    private Long id;

    private String username;

    private String role;
}

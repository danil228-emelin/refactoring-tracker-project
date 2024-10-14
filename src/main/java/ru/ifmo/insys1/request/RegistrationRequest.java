package ru.ifmo.insys1.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}

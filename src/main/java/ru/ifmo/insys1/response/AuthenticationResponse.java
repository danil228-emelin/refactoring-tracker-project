package ru.ifmo.insys1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private Integer id;
    private String token;
    private String role;
    private String username;
}

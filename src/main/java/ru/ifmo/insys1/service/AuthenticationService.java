package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.AuthenticationRequest;
import ru.ifmo.insys1.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
}

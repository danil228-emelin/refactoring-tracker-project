package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.AuthenticationRequest;
import ru.ifmo.insys1.request.RegistrationRequest;
import ru.ifmo.insys1.response.AuthenticationResponse;
import ru.ifmo.insys1.response.RegistrationResponse;

public interface SecurityService {

    RegistrationResponse register(RegistrationRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}

package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.RegistrationRequest;
import ru.ifmo.insys1.response.RegistrationResponse;

public interface RegistrationService {

    RegistrationResponse register(RegistrationRequest request);
}

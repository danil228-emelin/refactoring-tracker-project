package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.SecurityController;
import ru.ifmo.insys1.request.AuthenticationRequest;
import ru.ifmo.insys1.request.RegistrationRequest;
import ru.ifmo.insys1.response.RegistrationResponse;
import ru.ifmo.insys1.service.SecurityService;

@ApplicationScoped
public class SecurityControllerImpl implements SecurityController {

    @Inject
    private SecurityService securityService;

    @Override
    public Response register(RegistrationRequest registrationRequest) {
        RegistrationResponse register = securityService.register(registrationRequest);

        return Response.ok(register).build();
    }

    @Override
    public Response authenticate(AuthenticationRequest authenticationRequest) {
        return null;
    }
}

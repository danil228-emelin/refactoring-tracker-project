package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.AuthenticationController;
import ru.ifmo.insys1.request.AuthenticationRequest;
import ru.ifmo.insys1.response.AuthenticationResponse;
import ru.ifmo.insys1.service.AuthenticationService;

@ApplicationScoped
public class AuthenticationControllerImpl implements AuthenticationController {

    @Inject
    private AuthenticationService authenticationService;

    @Override
    public Response authenticate(AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        return Response.ok(response).build();
    }
}

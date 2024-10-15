package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.RegistrationController;
import ru.ifmo.insys1.request.RegistrationRequest;
import ru.ifmo.insys1.response.RegistrationResponse;
import ru.ifmo.insys1.service.RegistrationService;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@ApplicationScoped
public class RegistrationControllerImpl implements RegistrationController {

    @Inject
    private RegistrationService registrationService;

    @Override
    public Response register(RegistrationRequest registrationRequest) {
        RegistrationResponse register = registrationService.register(registrationRequest);

        return Response.status(CREATED)
                .entity(register)
                .build();
    }

}

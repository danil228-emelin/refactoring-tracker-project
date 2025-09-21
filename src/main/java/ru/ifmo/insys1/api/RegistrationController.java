package ru.ifmo.insys1.api;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.request.RegistrationRequest;

import static ru.ifmo.insys1.constants.APIConstants.REGISTRATION_URI;

@Path(REGISTRATION_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface RegistrationController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response register(@Valid RegistrationRequest registrationRequest);
}

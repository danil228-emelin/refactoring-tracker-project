package ru.ifmo.insys1.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.request.AuthenticationRequest;

import static ru.ifmo.insys1.constants.APIConstants.AUTHENTICATION_URI;

@Path(AUTHENTICATION_URI)
public interface AuthenticationController {

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response authenticate(@Valid AuthenticationRequest authenticationRequest);
}

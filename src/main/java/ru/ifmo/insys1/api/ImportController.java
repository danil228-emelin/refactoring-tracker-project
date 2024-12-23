package ru.ifmo.insys1.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.IMPORTS_URI;

@Path(IMPORTS_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface ImportController {

    @GET
    @JWT
    Response getAll();
}

package ru.ifmo.insys1.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.LABELS_URI;

@Path(LABELS_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface LabelsController {

    @GET
    @Path("/{id}")
    @JWT
    Response getLabel(@PathParam("id") Integer id);

    @POST
    @JWT
    Response createLabel();
}

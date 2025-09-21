package ru.ifmo.insys1.api;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.request.CoordinatesRequest;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.COORDINATES_URI;

@Path(COORDINATES_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface CoordinatesController {

    @GET
    @Path("/{id}")
    @JWT
    Response getCoordinates(@PathParam("id") Long id);

    @GET
    @JWT
    Response getAllCoordinates(@QueryParam("page") @DefaultValue("1") int page,
                               @QueryParam("size") @DefaultValue("10") int size);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response createCoordinates(@Valid CoordinatesRequest coordinates);

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response updateCoordinates(@PathParam("id") Long id, CoordinatesRequest coordinates);

    @DELETE
    @Path("/{id}")
    @JWT
    Response deleteCoordinates(@PathParam("id") Long id);
}

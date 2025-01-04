package ru.ifmo.insys1.api;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.request.LocationDTO;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.LOCATIONS_URI;

@Path(LOCATIONS_URI)
public interface LocationController {

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getLocation(@PathParam("id") Integer id);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getAllLocations();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response createLocation(@Valid LocationDTO location);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response deleteLocation(@PathParam("id") Integer id);
}

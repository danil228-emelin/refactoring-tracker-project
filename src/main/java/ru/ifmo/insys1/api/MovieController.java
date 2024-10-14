package ru.ifmo.insys1.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static ru.ifmo.insys1.constants.APIConstants.MOVIE_URI;
import static ru.ifmo.insys1.constants.RoleConstant.ADMIN;
import static ru.ifmo.insys1.constants.RoleConstant.USER;

@Path(MOVIE_URI)
public interface MovieController {

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ADMIN, USER})
    Response getMovie(@PathParam("id") Long id);
}

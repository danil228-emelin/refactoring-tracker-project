package ru.ifmo.insys1.api;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.request.PersonRequest;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.PERSONS_URI;

@Path(PERSONS_URI)
public interface PersonController {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getPerson(@QueryParam("id") Long id);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getAllPersons(@QueryParam("page") @DefaultValue("1") int page,
                           @QueryParam("size") @DefaultValue("10") int size);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response createPerson(@Valid PersonRequest person);

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response updatePerson(@PathParam("id") Long id, PersonRequest person);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response deletePerson(@PathParam("id") Long id);

    @GET
    @Path("/operators-without-oscar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getOperatorsWithoutOscar();

}

package ru.ifmo.insys1.api;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.request.CargoRequestDTO;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.CARGO_REQUESTS_URI;

@Path(CARGO_REQUESTS_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface CargoRequestController {

    @GET
    @Path("/{client-name}")
    @JWT
    Response getCargoRequestByClientName(@PathParam("client-name") String clientName);

    @GET
    @JWT
    Response getAllCargoRequests();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response createCargoRequest(@Valid CargoRequestDTO cargoRequestDTO);

    @DELETE
    @Path("/{id}")
    @JWT
    Response deleteCargoRequest(@PathParam("id") Integer id);
}

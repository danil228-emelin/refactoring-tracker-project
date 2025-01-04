package ru.ifmo.insys1.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.request.CreateCargoDTO;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.CARGOES_URI;

@Path(CARGOES_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface CargoesController {

    @GET
    @Path("/{sscc}")
    @JWT
    Response getCargoBySscc(@PathParam("sscc") String sscc);

    @POST
    @JWT
    Response createCargo(CreateCargoDTO request);
}

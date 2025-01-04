package ru.ifmo.insys1.api;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.constants.APIConstants;
import ru.ifmo.insys1.request.UpdateCargoStatus;
import ru.ifmo.insys1.security.JWT;

@Path(APIConstants.CARGO_STATUS_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface CargoStatusController {

    @PATCH
    @Path("/{sscc_code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response updateCargoStatus(@PathParam("sscc_code") String ssccCode, @Valid UpdateCargoStatus updateCargoStatus);
}

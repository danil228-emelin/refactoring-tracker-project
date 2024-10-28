package ru.ifmo.insys1.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.response.ApplicationDTO;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.ADMIN_URI;

@Path(ADMIN_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface AdminController {

    @POST
    @JWT
    Response submitApplication();

    @GET
    @JWT
    Response getApplications(@QueryParam("page") @DefaultValue("1") int page,
                             @QueryParam("size") @DefaultValue("10") int size);

    @PATCH
    @JWT
    @Consumes(MediaType.APPLICATION_JSON)
    Response acceptApplication(ApplicationDTO applicationDTO);

}

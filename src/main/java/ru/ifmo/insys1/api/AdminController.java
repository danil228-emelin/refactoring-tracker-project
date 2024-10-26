package ru.ifmo.insys1.api;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.constants.APIConstants;
import ru.ifmo.insys1.response.ApplicationDTO;
import ru.ifmo.insys1.security.JWT;

@Path(APIConstants.ADMIN_URI)
public interface AdminController {

    @POST
    @PermitAll
    @JWT
    Response submitApplication();

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getApplications(@QueryParam("page") @DefaultValue("1") int page,
                             @QueryParam("size") @DefaultValue("10") int size);

    @PATCH
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response acceptApplication(ApplicationDTO applicationDTO);

}

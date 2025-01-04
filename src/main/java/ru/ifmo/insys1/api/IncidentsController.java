package ru.ifmo.insys1.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.constants.APIConstants;
import ru.ifmo.insys1.request.IncidentRequest;
import ru.ifmo.insys1.security.JWT;

@Path(APIConstants.INCIDENTS_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface IncidentsController {

    @GET
    @Path("/{client-id}")
    @JWT
    Response getIncidents(@PathParam("client-id") Integer clientId);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response createIncident(IncidentRequest incidentRequest);
}

package ru.ifmo.insys1.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.NOTIFICATIONS_URI;

@Path(NOTIFICATIONS_URI)
public interface NotificationsController {

    @GET
    @Path("/{owner_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getNotifications(@PathParam("owner_id") Integer ownerId);
}

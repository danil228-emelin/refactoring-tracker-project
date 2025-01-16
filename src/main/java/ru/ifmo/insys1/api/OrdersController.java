package ru.ifmo.insys1.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.request.OrderDeliveryDateRequest;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.ORDERS_URI;

@Path(ORDERS_URI)
@Produces(MediaType.APPLICATION_JSON)
public interface OrdersController {

    @GET
    @Path("/user/{id}")
    @JWT
    Response getOrdersByUserId(@PathParam("id") Integer id);

    @GET
    @Path("/{id}")
    @JWT
    Response getOrder(@PathParam("id") Integer id);

    @POST
    @Path("/{client-name}")
    @JWT
    Response createOrder(@PathParam("client-name") String clientName);

    @PATCH
    @Path("/{id}")
    @JWT
    @Consumes(MediaType.APPLICATION_JSON)
    Response changeDeliveryDate(@PathParam("id") Integer id, OrderDeliveryDateRequest deliveryDateRequest);
}

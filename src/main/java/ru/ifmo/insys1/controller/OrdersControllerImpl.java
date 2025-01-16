package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.OrdersController;
import ru.ifmo.insys1.request.OrderDeliveryDateRequest;
import ru.ifmo.insys1.service.OrderService;

@ApplicationScoped
public class OrdersControllerImpl implements OrdersController {

    @Inject
    private OrderService orderService;

    @Override
    public Response getOrdersByUserId(Integer id) {
        return Response.ok(orderService.getOrdersByUserId(id))
                .build();
    }

    @Override
    public Response getOrder(Integer id) {
        return Response.ok(orderService.getOrder(id))
                .build();
    }


    @Override
    public Response createOrder(String clientName) {
        return Response.status(Response.Status.CREATED)
                .entity(orderService.createOrder(clientName))
                .build();
    }

    @Override
    public Response changeDeliveryDate(Integer id, OrderDeliveryDateRequest deliveryDateRequest) {
        return Response.ok(orderService.changeDeliveryDate(id, deliveryDateRequest))
                .build();
    }
}

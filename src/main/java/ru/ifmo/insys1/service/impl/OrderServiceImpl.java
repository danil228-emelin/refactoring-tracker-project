package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.dao.OrderDAO;
import ru.ifmo.insys1.entity.Order;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.OrderDeliveryDateRequest;
import ru.ifmo.insys1.response.OrderResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.OrderService;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static ru.ifmo.insys1.constants.RoleConstant.MANAGER;
import static ru.ifmo.insys1.constants.RoleConstant.OPERATOR;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDAO orderDAO;

    @Inject
    private SecurityManager securityManager;

    @Override
    public Optional<OrderResponse> getOrder(Integer id) {
        return orderDAO.get(id)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public OrderResponse createOrder(String clientName) {
        if (!securityManager.hasAnyRole(OPERATOR, MANAGER)) {
            throw new ServiceException(Response.Status.FORBIDDEN, "You are not allowed to add another order");
        }
        return mapToResponse(orderDAO.persist(clientName));
    }

    @Override
    @Transactional
    public OrderResponse changeDeliveryDate(Integer id, OrderDeliveryDateRequest deliveryDateRequest) {
        return mapToResponse(orderDAO.changeDeliveryDate(id, deliveryDateRequest));
    }

    private OrderResponse mapToResponse(Order order) {
        var orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setClientName(order.getClient().getUsername());
        if (order.getDeliveryDate() != null) {
            orderResponse.setDeliveryDate(order.getDeliveryDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        if (order.getCreationDate() != null) {
            orderResponse.setCreationDate(order.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        return orderResponse;
    }
}

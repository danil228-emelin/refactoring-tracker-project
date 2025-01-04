package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.OrderDeliveryDateRequest;
import ru.ifmo.insys1.response.OrderResponse;

import java.util.Optional;

public interface OrderService {

    Optional<OrderResponse> getOrder(Integer id);

    OrderResponse createOrder(String clientName);

    OrderResponse changeDeliveryDate(Integer id, OrderDeliveryDateRequest deliveryDateRequest);
}

package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.OrderDeliveryDateRequest;
import ru.ifmo.insys1.response.CargoRequestResponse;
import ru.ifmo.insys1.response.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderResponse> getOrdersByUserId(Integer id);

    Optional<OrderResponse> getOrder(Integer id);

    OrderResponse createOrder(String clientName);

    OrderResponse changeDeliveryDate(Integer id, OrderDeliveryDateRequest deliveryDateRequest);
}

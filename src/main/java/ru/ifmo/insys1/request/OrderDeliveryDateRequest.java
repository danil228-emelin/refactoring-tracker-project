package ru.ifmo.insys1.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDeliveryDateRequest {

    private String deliveryDate;
}

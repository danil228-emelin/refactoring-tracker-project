package ru.ifmo.insys1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoResponse {

    private Integer id;
    private String name;
    private String destinationCenter;
    private String receptionCenter;
    private CargoType cargoType;
    private String registrationDate;
    private String currentLocation;
    private LocationType currentLocationType;
    private CargoStatusDelivery cargoStatusDelivery;
    private Short weight;
    private String ssccCode;
    private String deliveryDate;
    private String clientUsername;
}

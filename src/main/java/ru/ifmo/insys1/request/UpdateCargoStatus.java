package ru.ifmo.insys1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.CargoStatusDelivery;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCargoStatus {

    private Integer locationId;
    private CargoStatusDelivery cargoStatusDelivery;
}

package ru.ifmo.insys1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.CargoType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoRequestResponse {

    private Integer id;
    private String name;
    private CargoType cargoType;
    private Integer receptionCenterId;
    private Integer destinationCenterId;
    private String creationDate;
}

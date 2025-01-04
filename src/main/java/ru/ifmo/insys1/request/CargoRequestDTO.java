package ru.ifmo.insys1.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.CargoType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoRequestDTO {

    @NotEmpty
    private String name;

    @NotNull
    private CargoType cargoType;

    @NotNull
    private Integer receptionCenter;

    @NotNull
    private Integer destinationCenter;
}

package ru.ifmo.insys1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCargoDTO {

    private Integer cargoRequestId;
    private Short weight;
    private Integer orderId;
    private Integer labelId;
}

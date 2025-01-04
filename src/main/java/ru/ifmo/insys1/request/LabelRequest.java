package ru.ifmo.insys1.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabelRequest {

    @Max(value = 912)
    private float x;

    @NotNull
    @Min(value = -959)
    private Integer y;
}

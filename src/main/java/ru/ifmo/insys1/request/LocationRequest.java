package ru.ifmo.insys1.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {

    private long x;

    private int y;

    @NotNull
    private String name;
}

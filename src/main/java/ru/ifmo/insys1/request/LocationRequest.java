package ru.ifmo.insys1.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationRequest {

    private long x;

    private int y;

    @NotNull
    private String name;
}

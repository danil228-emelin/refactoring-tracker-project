package ru.ifmo.insys1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationDTO {

    private long x;

    private int y;

    @NotNull
    private String name;
}

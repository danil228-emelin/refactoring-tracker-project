package ru.ifmo.insys1.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.ifmo.insys1.entity.Color;
import ru.ifmo.insys1.entity.Country;

import java.time.LocalDateTime;

@Data
public class PersonRequest {

    @NotEmpty
    private String name;

    @NotNull
    private Color eyeColor;

    @NotNull
    private Color hairColor;

    @NotNull
    private Long location;

    @NotNull
    private LocalDateTime birthday;

    @NotNull
    private Country nationality;
}

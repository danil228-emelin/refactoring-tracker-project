package ru.ifmo.insys1.response.dto;

import lombok.Data;
import ru.ifmo.insys1.entity.Color;
import ru.ifmo.insys1.entity.Country;

import java.time.LocalDateTime;

@Data
public class PersonDTO {

    private String name;

    private Color eyeColor;

    private Color hairColor;

    private LocationDTO location;

    private LocalDateTime birthday;

    private Country nationality;
}

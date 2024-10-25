package ru.ifmo.insys1.response;

import lombok.Data;
import ru.ifmo.insys1.entity.Color;
import ru.ifmo.insys1.entity.Country;

import java.time.LocalDateTime;

@Data
public class PersonResponse {

    private Long id;

    private String name;

    private Color eyeColor;

    private Color hairColor;

    private Long location;

    private LocalDateTime birthday;

    private Country nationality;
}

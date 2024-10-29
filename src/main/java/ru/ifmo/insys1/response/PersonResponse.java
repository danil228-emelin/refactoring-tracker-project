package ru.ifmo.insys1.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.Color;
import ru.ifmo.insys1.entity.Country;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {

    private Long id;

    private String name;

    private Color eyeColor;

    private Color hairColor;

    private Long locationId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime birthday;

    private Country nationality;
}

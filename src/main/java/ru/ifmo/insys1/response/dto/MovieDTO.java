package ru.ifmo.insys1.response.dto;

import lombok.Data;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.entity.MpaaRating;

@Data
public class MovieDTO {

    private String name;

    private CoordinatesDTO coordinates;

    private long oscarsCount;

    private Double budget;

    private float totalBoxOffice;

    private MpaaRating mpaaRating;

    private PersonDTO director;

    private PersonDTO screenwriter;

    private PersonDTO operator;

    private int length;

    private Integer goldenPalmCount;

    private String tagline;

    private MovieGenre genre;
}

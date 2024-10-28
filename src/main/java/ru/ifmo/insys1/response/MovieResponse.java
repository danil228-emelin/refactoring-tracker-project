package ru.ifmo.insys1.response;

import lombok.Data;
import ru.ifmo.insys1.request.CoordinatesRequest;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.entity.MpaaRating;

@Data
public class MovieResponse {

    private String name;

    private CoordinatesResponse coordinates;

    private long oscarsCount;

    private Double budget;

    private float totalBoxOffice;

    private MpaaRating mpaaRating;

    private PersonResponse director;

    private PersonResponse screenwriter;

    private PersonResponse operator;

    private int length;

    private Integer goldenPalmCount;

    private String tagline;

    private MovieGenre genre;
}

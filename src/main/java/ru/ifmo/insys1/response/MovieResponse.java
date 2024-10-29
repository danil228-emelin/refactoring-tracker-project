package ru.ifmo.insys1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.entity.MpaaRating;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    private Long id;

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

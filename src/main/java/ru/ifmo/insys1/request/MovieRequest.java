package ru.ifmo.insys1.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.entity.MpaaRating;

@Data
public class MovieRequest {

    @NotEmpty
    private String name;

    @NotNull
    private Long coordinates;

    @Min(value = 0)
    private long oscarsCount;

    @Min(value = 0)
    private Double budget;

    @Min(value = 0)
    private float totalBoxOffice;

    private MpaaRating mpaaRating;

    private Long director;

    private Long screenwriter;

    private Long operator;

    @Min(value = 0)
    private int length;

    @Min(value = 0)
    private Integer goldenPalmCount;

    @NotEmpty
    private String tagline;

    @NotNull
    private MovieGenre genre;
}

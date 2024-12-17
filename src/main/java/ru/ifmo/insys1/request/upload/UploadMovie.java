package ru.ifmo.insys1.request.upload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.entity.MpaaRating;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadMovie {

    @NotEmpty
    private String name;

    @NotNull
    @Valid
    private UploadCoordinates coordinates;

    @Min(value = 0)
    private long oscarsCount;

    @Min(value = 0)
    private Double budget;

    @Min(value = 0)
    private float totalBoxOffice;

    private MpaaRating mpaaRating;

    @Valid
    private UploadPerson director;

    @Valid
    private UploadPerson screenwriter;

    @Valid
    private UploadPerson operator;

    @Min(value = 0)
    private int length;

    @Min(value = 0)
    private Integer goldenPalmCount;

    @NotEmpty
    private String tagline;

    @NotNull
    private MovieGenre genre;
}

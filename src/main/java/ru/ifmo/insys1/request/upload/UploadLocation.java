package ru.ifmo.insys1.request.upload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadLocation {

    private long x;

    private int y;

    @NotNull
    private String name;
}

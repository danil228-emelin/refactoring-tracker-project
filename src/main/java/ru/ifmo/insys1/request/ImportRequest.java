package ru.ifmo.insys1.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImportRequest {

    private Boolean isSuccess;

    private Integer count;
}

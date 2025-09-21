package ru.ifmo.insys1.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportResponse {

    private Long id;

    private Boolean isSuccess;

    private Integer count;

    private Long createdBy;
}

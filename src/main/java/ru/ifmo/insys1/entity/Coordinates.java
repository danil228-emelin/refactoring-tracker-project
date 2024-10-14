package ru.ifmo.insys1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
public class Coordinates extends AuditingEntity {

    // todo: message
    @Max(value = 912)
    private float x;

    // todo: message
    @NotNull
    @Min(value = -959)
    private Integer y;
}

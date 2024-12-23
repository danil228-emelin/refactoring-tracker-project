package ru.ifmo.insys1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
@ToString
public class Coordinates extends AuditingEntity {

    @Max(value = 912)
    private float x;

    @NotNull
    @Min(value = -959)
    private Integer y;
}

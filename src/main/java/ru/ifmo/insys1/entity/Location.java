package ru.ifmo.insys1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "location")
@Getter
@Setter
public class Location extends AuditingEntity {

    private long x;

    private int y;

    @NotNull
    private String name;
}

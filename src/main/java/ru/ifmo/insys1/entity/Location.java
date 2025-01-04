package ru.ifmo.insys1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location extends BaseEntity {

    private String address;

    @Enumerated(EnumType.STRING)
    private LocationType type;

    @NotEmpty
    private String name;
}

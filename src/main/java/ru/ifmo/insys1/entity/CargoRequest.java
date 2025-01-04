package ru.ifmo.insys1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cargo_requests")
@Getter
@Setter
public class CargoRequest extends OwnedEntity {

    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "reception_center_oid",
            nullable = false,
            referencedColumnName = "id"
    )
    private Location receptionCenter;

    @ManyToOne
    @JoinColumn(
            name = "destination_center_oid",
            nullable = false,
            referencedColumnName = "id"
    )
    private Location destinationCenter;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_type")
    private CargoType cargoType;

    @Column(name = "creation_date", insertable = false)
    private LocalDateTime creationDate;
}

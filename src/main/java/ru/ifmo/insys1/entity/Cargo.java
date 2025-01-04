package ru.ifmo.insys1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cargoes")
@Getter
@Setter
public class Cargo extends BaseEntity {

    @NotNull
    private Short weight;

    @ManyToOne
    @JoinColumn(
            name = "order_oid",
            nullable = false,
            referencedColumnName = "id"
    )
    private Order order;

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

    @OneToOne
    @JoinColumn(
            name = "cargo_status_oid",
            nullable = false,
            referencedColumnName = "id"
    )
    private CargoStatus cargoStatus;

    @ManyToOne
    @JoinColumn(
            name = "label_oid",
            nullable = false,
            referencedColumnName = "id"
    )
    private Label label;

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo_type")
    private CargoType cargoType;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
}

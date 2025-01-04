package ru.ifmo.insys1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cargo_status")
@Getter
@Setter
public class CargoStatus extends BaseEntity {

    @ManyToOne
    @JoinColumn(
            name = "location_oid",
            referencedColumnName = "id",
            nullable = false
    )
    private Location location;

    @Column(name = "cargo_status")
    @Enumerated(EnumType.STRING)
    private CargoStatusDelivery cargoStatusDelivery;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}

package ru.ifmo.insys1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Getter
@Setter
public class Incident extends BaseEntity {

    @ManyToOne
    @JoinColumn(
            name = "cargo_oid",
            referencedColumnName = "id",
            nullable = false
    )
    private Cargo cargo;

    @Enumerated(EnumType.STRING)
    private IncidentType type;

    private String description;

    @Column(name = "occurrence_date")
    private LocalDateTime occurrenceDate;
}

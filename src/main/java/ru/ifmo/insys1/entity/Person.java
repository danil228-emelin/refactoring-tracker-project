package ru.ifmo.insys1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends AuditingEntity {

    @NotEmpty
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "eye_color")
    private Color eyeColor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hair_color")
    private Color hairColor;

    @ManyToOne
    @JoinColumn(
            name = "location_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Location location;

    @NotNull
    private LocalDateTime birthday;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Country nationality;
}

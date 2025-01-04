package ru.ifmo.insys1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification extends OwnedEntity {

    @Column(name = "description")
    private String description;

    @Column(name = "is_positive")
    private Boolean isPositive;
}

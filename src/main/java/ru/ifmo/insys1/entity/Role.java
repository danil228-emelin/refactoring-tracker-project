package ru.ifmo.insys1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// todo: do second-level cache
public class Role extends BaseEntity {

    @Column(
            name = "role_name",
            nullable = false,
            unique = true
    )
    private String roleName;
}

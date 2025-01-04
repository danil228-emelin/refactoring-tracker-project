package ru.ifmo.insys1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(OwnedEntityListener.class)
public abstract class OwnedEntity extends BaseEntity {

    @Column(
            name = "owner_oid",
            nullable = false,
            updatable = false
    )
    private Integer ownerOid;
}

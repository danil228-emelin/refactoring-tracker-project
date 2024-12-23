package ru.ifmo.insys1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "import")
@Getter
@Setter
public class Import extends AuditingEntity {

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "count")
    private Integer count;
}

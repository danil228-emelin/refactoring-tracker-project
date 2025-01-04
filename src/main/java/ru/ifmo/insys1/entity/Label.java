package ru.ifmo.insys1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "labels")
@Getter
@Setter
public class Label extends BaseEntity {

    @Column(name = "sscc_code")
    private String ssccCode;

    @Column(name = "generation_date")
    private LocalDateTime generationDate;
}

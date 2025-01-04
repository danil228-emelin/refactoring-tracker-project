package ru.ifmo.insys1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<Cargo> cargoes;

    @ManyToOne
    @JoinColumn(
            name = "client_oid",
            nullable = false,
            referencedColumnName = "id"
    )
    private User client;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;
}

package ru.ifmo.insys1.entity;

import jakarta.persistence.PrePersist;

public class AuditListener {

    // todo: inject current User
//    @Inject
//    private ;

    @PrePersist
    public void setCreatedOn(Object auditable) {
        if (auditable instanceof AuditingEntity) {
            AuditingEntity audit = (AuditingEntity) auditable;

        }
    }
}

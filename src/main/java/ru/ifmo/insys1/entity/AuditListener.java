package ru.ifmo.insys1.entity;

import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;
import ru.ifmo.insys1.security.SecurityManager;

import java.time.LocalDateTime;

public class AuditListener {

    @Inject
    private SecurityManager securityManager;

    @PrePersist
    public void setCreatedOn(Object auditable) {
        if (auditable instanceof AuditingEntity audit) {
            audit.setCreatedBy(securityManager.getCallerPrincipal());
            audit.setCreationDate(LocalDateTime.now());
        }
    }
}

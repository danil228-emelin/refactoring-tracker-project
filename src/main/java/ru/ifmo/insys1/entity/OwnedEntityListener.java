package ru.ifmo.insys1.entity;

import jakarta.inject.Inject;
import jakarta.persistence.PrePersist;
import ru.ifmo.insys1.security.SecurityManager;

public class OwnedEntityListener {

    @Inject
    private SecurityManager securityManager;

    @PrePersist
    public void setCreatedOn(Object auditable) {
        if (auditable instanceof OwnedEntity audit) {
            audit.setOwnerOid(securityManager.getCallerPrincipal());
        }
    }
}

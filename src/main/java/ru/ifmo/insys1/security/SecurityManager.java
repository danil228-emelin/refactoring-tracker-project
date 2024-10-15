package ru.ifmo.insys1.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.Set;

@RequestScoped
public class SecurityManager {

    @Inject
    private AuthenticatedCaller authenticatedCaller;

    public boolean hasAnyRole(Set<String> roles) {
        Set<String> callerRoles = authenticatedCaller.getRoles();

        if (callerRoles == null) {
            return false;
        }

        for (String role : roles) {
            if (callerRoles.contains(role)) {
                return true;
            }
        }

        return false;
    }

    public boolean isAnonymous() {
        return !authenticatedCaller.isAuthenticated();
    }

    public Long getCallerPrincipal() {
        return authenticatedCaller.getId();
    }
}

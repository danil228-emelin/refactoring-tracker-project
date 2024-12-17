package ru.ifmo.insys1.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import ru.ifmo.insys1.exception.ServiceException;

import java.util.Objects;
import java.util.Set;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;
import static ru.ifmo.insys1.constants.RoleConstant.ADMIN;
import static ru.ifmo.insys1.constants.RoleConstant.USER;

@RequestScoped
public class SecurityManager {

    @Inject
    private AuthenticatedCaller authenticatedCaller;

    public boolean hasAnyRole(String... roles) {
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

    public Long getCallerPrincipal() {
        return authenticatedCaller.getId();
    }

    public void throwIfUserHasNotAccessToResource(Long resourceOwner) {
        if (hasAnyRole(ADMIN)) {
            return;
        }

        boolean isUserHasAccess = hasAnyRole(USER) &&
                Objects.equals(resourceOwner, getCallerPrincipal());

        if (!isUserHasAccess) {
            throwForbiddenException();
        }
    }

    public void throwForbiddenException() {
        throw new ServiceException(
                FORBIDDEN,
                "You do not have permission to access this resource"
        );
    }
}

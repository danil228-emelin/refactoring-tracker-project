package ru.ifmo.insys1.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.dao.UserDAO;
import ru.ifmo.insys1.entity.User;

import java.util.Optional;
import java.util.Set;

@ApplicationScoped
@Slf4j
public class PostgresIdentityStoreHandler {

    @Inject
    private UserDAO userDAO;

    @Inject
    private AuthenticatedCaller authenticatedCaller;

    public AuthenticatedCaller validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential upc) {
            String username = upc.getCaller();

            Optional<User> optionalUser = userDAO.findByUsername(username);

            if (optionalUser.isEmpty()) {
                log.warn("User {} not found", username);

                return authenticatedCaller;
            }

            User user = optionalUser.get();

            Set<String> roles = Set.of(user.getRole().getRoleName());

            log.warn("Found user - {}; Roles - {}", username, roles);

            initCaller(authenticatedCaller, user, roles);

            return authenticatedCaller;
        }

        log.warn("Credential type is not available");

        return authenticatedCaller;
    }

    private void initCaller(AuthenticatedCaller authenticatedCaller, User user, Set<String> roles) {
        authenticatedCaller.setId(user.getId());
        authenticatedCaller.setAuthenticated(true);
        authenticatedCaller.setRoles(roles);
    }
}
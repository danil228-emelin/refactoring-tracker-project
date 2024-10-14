package ru.ifmo.insys1.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import ru.ifmo.insys1.dao.UserDAO;
import ru.ifmo.insys1.entity.User;

import java.util.Optional;
import java.util.Set;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class PostgresIdentityStore implements IdentityStore {

    @Inject
    private UserDAO userDAO;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential upc) {
            String username = upc.getCaller();

            Optional<User> optionalUser = userDAO.findByUsername(username);

            if (optionalUser.isEmpty()) {
                return INVALID_RESULT;
            }

            User user = optionalUser.get();

            return new CredentialValidationResult(
                    username,
                    Set.of(user.getRole().getRoleName())
            );
        }

        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
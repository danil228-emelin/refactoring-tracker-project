package ru.ifmo.insys1.security;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;

import java.util.HashSet;
import java.util.Set;

import static jakarta.interceptor.Interceptor.Priority.APPLICATION;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.INVALID;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.NOT_VALIDATED;

@Alternative
@Priority(APPLICATION)
@ApplicationScoped
public class IdentityStoreHandlerImpl implements IdentityStoreHandler {

    @Inject
    Instance<IdentityStore> identityStores;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CredentialValidationResult result = null;
        Set<String> groups = new HashSet<>();

        for (IdentityStore identityStore : identityStores) {
            result = identityStore.validate(credential);
            if (result.getStatus() == NOT_VALIDATED) {
                continue;
            }

            if (result.getStatus() == INVALID) {
                return INVALID_RESULT;
            }

            groups.addAll(result.getCallerGroups());
        }

        return new CredentialValidationResult(
                result.getCallerPrincipal(), groups);
    }
}

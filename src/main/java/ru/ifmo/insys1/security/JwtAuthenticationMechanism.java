package ru.ifmo.insys1.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.Optional;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;

@ApplicationScoped
public class JwtAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Inject
    private JwtUtil jwtUtil;

    @Override
    public AuthenticationStatus validateRequest(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            HttpMessageContext httpMessageContext) throws AuthenticationException {

        Optional<String> accessToken = extractAccessToken(httpServletRequest);

        if (accessToken.isEmpty()) {
            return httpMessageContext.doNothing();
        }

        String username = jwtUtil.validateTokenAndRetrieveClaim(accessToken.get());

        var result = identityStoreHandler.validate(new UsernamePasswordCredential(username,  ""));

        if (result.getStatus() != VALID) {
            return httpMessageContext.responseUnauthorized();
        }

        return httpMessageContext.notifyContainerAboutLogin(
                result.getCallerPrincipal(),
                result.getCallerGroups());
    }

    private Optional<String> extractAccessToken(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.split(" ")[1].trim());
        }

        return Optional.empty();
    }
}

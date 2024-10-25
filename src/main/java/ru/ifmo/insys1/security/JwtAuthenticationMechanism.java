package ru.ifmo.insys1.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
@Priority(Priorities.AUTHENTICATION)
@Slf4j
@JWT
public class JwtAuthenticationMechanism implements ContainerRequestFilter {

    @Inject
    private PostgresIdentityStoreHandler identityStoreHandler;

    @Inject
    private JwtUtil jwtUtil;

    @Override
    public void filter(ContainerRequestContext crc) {
        Optional<String> optionalAccessToken = extractAccessToken(crc);

        if (optionalAccessToken.isEmpty()) {
            log.warn("No access token found in request");

            crc.abortWith(
                    Response.status(UNAUTHORIZED)
                            .build()
            );

            return;
        }

        String accessToken = optionalAccessToken.get();

        log.warn("Access token found in request: {}", accessToken);

        String username;

        try {
            username = jwtUtil.validateTokenAndRetrieveClaim(accessToken);
        } catch (JWTVerificationException e) {
            log.error(e.getMessage(), e);

            crc.abortWith(
                    Response.status(UNAUTHORIZED)
                            .build()
            );

            return;
        }

        var result = identityStoreHandler.validate(
                new UsernamePasswordCredential(username, "")
        );

        if (!result.isAuthenticated()) {
            log.error("Invalid access token: {}", accessToken);

            crc.abortWith(
                    Response.status(UNAUTHORIZED)
                            .build()
            );

            return;
        }

        log.warn(
                "Access token validated, user - {}, roles - {}",
                username,
                result.getRoles()
        );
    }

    private Optional<String> extractAccessToken(ContainerRequestContext crc) {
        String authHeader = crc.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.split(" ")[1].trim());
        }

        return Optional.empty();
    }

}

package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.dao.UserDAO;
import ru.ifmo.insys1.entity.User;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.AuthenticationRequest;
import ru.ifmo.insys1.response.AuthenticationResponse;
import ru.ifmo.insys1.security.JwtUtil;
import ru.ifmo.insys1.security.PasswordHash;
import ru.ifmo.insys1.service.AuthenticationService;

import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

@ApplicationScoped
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private PasswordHash passwordHash;

    @Inject
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String username = request.getUsername();

        Optional<User> optionalUser = userDAO.findByUsername(username);

        if (optionalUser.isEmpty()) {
            log.warn("User not found: {}", username);
            throw new ServiceException(UNAUTHORIZED, "Incorrect username or password");
        }

        User user = optionalUser.get();

        boolean verified = passwordHash.verify(
                request.getPassword(),
                user.getPassword()
        );

        if (!verified) {
            log.warn("Authentication request with incorrect password");
            throw new ServiceException(UNAUTHORIZED, "Incorrect username or password");
        }

        String accessToken = jwtUtil.generateAccessToken(username);

        return new AuthenticationResponse(
                accessToken,
                user.getRole().getRoleName(),
                user.getUsername()
        );
    }

}

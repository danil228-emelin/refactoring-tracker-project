package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.api.MovieController;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.MovieService;

import static jakarta.ws.rs.core.Response.Status.FORBIDDEN;

@ApplicationScoped
@Slf4j
public class MovieControllerImpl implements MovieController {

    @Inject
    private SecurityManager securityManager;

    @Inject
    private MovieService movieService;

    public Response getMovie(Long id) {
        if (securityManager.isAnonymous()) {
            throw new ServiceException(FORBIDDEN, "You do not have permission to access this resource");
        }

        return Response.ok(movieService.getMovie(id)).build();
    }
}

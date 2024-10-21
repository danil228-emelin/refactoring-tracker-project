package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.MovieController;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.MovieService;

@ApplicationScoped
public class MovieControllerImpl implements MovieController {

    @Inject
    private SecurityManager securityManager;

    @Inject
    private MovieService movieService;

    public Response getMovie(Long id) {
        securityManager.throwIfAnonymous();

        return Response.ok(movieService.getMovie(id)).build();
    }
}

package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.MovieController;
import ru.ifmo.insys1.request.MovieRequest;
import ru.ifmo.insys1.service.MovieService;

@ApplicationScoped
public class MovieControllerImpl implements MovieController {

    @Inject
    private MovieService movieService;

    @Override
    public Response getMovie(Long id) {
        return Response.ok(movieService.getMovie(id))
                .build();
    }

    @Override
    public Response getAllMovies(int page, int size) {
        var movies = movieService.getAllMovies(page, size);

        return Response.ok(movies)
                .build();
    }

    @Override
    public Response createMovie(MovieRequest movie) {
        var created = movieService.createMovie(movie);

        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @Override
    public Response updateMovie(Long id, MovieRequest movie) {
        var updated = movieService.updateMovie(id, movie);

        return Response.ok(updated)
                .build();
    }

    @Override
    public Response deleteMovie(Long id) {
        movieService.deleteMovie(id);

        return Response.status(Response.Status.NO_CONTENT)
                .build();
    }
}

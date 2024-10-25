package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.MovieRequest;
import ru.ifmo.insys1.response.MovieResponse;

import java.util.List;

public interface MovieService {

    MovieResponse getMovie(Long id);

    List<MovieResponse> getAllMovies(int page, int size);

    MovieResponse createMovie(MovieRequest movie);

    MovieResponse updateMovie(Long id, MovieRequest movie);

    void deleteMovie(Long id);
}

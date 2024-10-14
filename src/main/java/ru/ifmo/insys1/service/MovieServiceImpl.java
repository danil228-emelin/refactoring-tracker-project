package ru.ifmo.insys1.service;

import jakarta.enterprise.context.ApplicationScoped;
import ru.ifmo.insys1.entity.Movie;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {

    @Override
    public Movie getMovie(Long id) {
        return null;
    }
}

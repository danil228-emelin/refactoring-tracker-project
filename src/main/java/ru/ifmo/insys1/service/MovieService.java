package ru.ifmo.insys1.service;

import ru.ifmo.insys1.dto.MovieDTO;

public interface MovieService {

    MovieDTO getMovie(Long id);
}

package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.MovieDAO;
import ru.ifmo.insys1.entity.Movie;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.dto.MovieDTO;
import ru.ifmo.insys1.service.MovieService;

import java.util.Optional;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieDAO movieDAO;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public MovieDTO getMovie(Long id) {
        Optional<Movie> optionalMovieById = movieDAO.findById(id);

        if (optionalMovieById.isEmpty()) {
            throw new ServiceException(Response.Status.NOT_FOUND, "Movie not found");
        }

        Movie movie = optionalMovieById.get();

        return modelMapper.map(movie, MovieDTO.class);
    }
}

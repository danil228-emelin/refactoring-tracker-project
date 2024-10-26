package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.MovieDAO;
import ru.ifmo.insys1.entity.Movie;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.entity.Person;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.MovieRequest;
import ru.ifmo.insys1.response.MovieResponse;
import ru.ifmo.insys1.response.PersonResponse;
import ru.ifmo.insys1.service.MovieService;
import ru.ifmo.insys1.service.PersonService;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieDAO movieDAO;

    @Inject
    private ModelMapper modelMapper;

    @Inject
    private PersonService personService;

    @Override
    public MovieResponse getMovie(Long id) {
        Optional<Movie> optionalMovie = movieDAO.findById(id);

        if (optionalMovie.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Movie not found");
        }

        return modelMapper.map(optionalMovie.get(), MovieResponse.class);
    }

    @Override
    public List<MovieResponse> getAllMovies(int page, int size) {
        List<Movie> allMovies = movieDAO.findAll(page, size);

        return allMovies.stream()
                .map(m -> modelMapper.map(m, MovieResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public MovieResponse createMovie(MovieRequest movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);

        setMovieDependencies(movieDTO, movie);

        movieDAO.save(movie);

        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(Long id, MovieRequest movieDTO) {
        Optional<Movie> optionalMovie = movieDAO.findById(id);

        if (optionalMovie.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Movie not found");
        }

        Movie movie = optionalMovie.get();
        modelMapper.map(movieDTO, movie);
        setMovieDependencies(movieDTO, movie);

        movieDAO.update(movie);

        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        movieDAO.delete(id);
    }

    @Override
    @Transactional
    public void deleteMovieByTagline(String tagline) {
        movieDAO.deleteMovieByTagline(tagline);
    }

    @Override
    public Long getCountMoviesWithTagline(String tagline) {
        return movieDAO.getCountMoviesWithTagline(tagline);
    }

    @Override
    public Long getCountMoviesWithGenre(MovieGenre movieGenre) {
        return movieDAO.getCountMoviesWithGenre(movieGenre);
    }

    @Override
    public List<PersonResponse> getOperatorsWithoutOscar() {
        return movieDAO.getOperatorsWithoutOscar()
                .stream()
                .map(operator -> modelMapper.map(operator, PersonResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public void incrementOscarsCountForAllMoviesWithRCategory() {
        movieDAO.incrementOscarsCountForAllMoviesWithRCategory();
    }

    private void setMovieDependencies(MovieRequest movieDTO, Movie movie) {
        if (movieDTO.getDirector() != null) {
            Person director = modelMapper.map(
                    personService.getPerson(movieDTO.getDirector()),
                    Person.class
            );

            movie.setDirector(director);
        }

        if (movieDTO.getScreenwriter() != null) {
            Person screenwriter = modelMapper.map(
                    personService.getPerson(movieDTO.getScreenwriter()),
                    Person.class
            );

            movie.setScreenwriter(screenwriter);
        }

        if (movieDTO.getOperator() != null) {
            Person operator = modelMapper.map(
                    personService.getPerson(movieDTO.getOperator()),
                    Person.class
            );

            movie.setOperator(operator);
        }
    }
}

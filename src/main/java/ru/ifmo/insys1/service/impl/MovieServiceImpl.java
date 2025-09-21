package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Pair;
import ru.ifmo.insys1.dao.MovieDAO;
import ru.ifmo.insys1.entity.Coordinates;
import ru.ifmo.insys1.entity.Movie;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.entity.Person;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.ImportRequest;
import ru.ifmo.insys1.request.MovieRequest;
import ru.ifmo.insys1.request.upload.UploadMovie;
import ru.ifmo.insys1.response.MovieResponse;
import ru.ifmo.insys1.response.PagedResult;
import ru.ifmo.insys1.response.PersonResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.CoordinatesService;
import ru.ifmo.insys1.service.ImportService;
import ru.ifmo.insys1.service.MovieService;
import ru.ifmo.insys1.service.PersonService;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieDAO movieDAO;

    @Inject
    private ModelMapper modelMapper;

    @Inject
    private PersonService personService;

    @Inject
    private CoordinatesService coordinatesService;

    @Inject
    private ImportService importService;

    @Inject
    private SecurityManager securityManager;

    @Override
    public MovieResponse getMovie(Long id) {
        Optional<Movie> optionalMovie = movieDAO.findById(id);
        if (optionalMovie.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Movie not found");
        }
        return modelMapper.map(optionalMovie.get(), MovieResponse.class);
    }

    @Override
    @Transactional
    public List<Long> uploadAll(List<UploadMovie> movies, Pair<String, String> fileUrl) {
        log.info("Received movies with size: {}", movies.size());
        List<Movie> mappedEntities = toEntities(movies);
        return persistMovies(mappedEntities, fileUrl.getRight());
    }

    private List<Movie> toEntities(List<UploadMovie> parsedMovies) {
        return parsedMovies.stream()
                .map(upload -> uploadToEntity(upload, new Movie()))
                .toList();
    }

    @Override
    public PagedResult getAllMovies(int page, int size, String filter, String filterColumn, String sorted) {
        PagedResult result = movieDAO.findAll(page, size, filter, filterColumn, sorted);
        List<?> movieResponses = result.getResults()
                .stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .toList();
        result.setResults(movieResponses);
        return result;
    }

    @Override
    @Transactional
    public MovieResponse createMovie(MovieRequest movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        dtoToEntity(movieDTO, movie);
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
        securityManager.throwIfUserHasNotAccessToResource(movie.getCreatedBy());
        modelMapper.map(movieDTO, movie);

        dtoToEntity(movieDTO, movie);
        movieDAO.update(movie);
        return modelMapper.map(movie, MovieResponse.class);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        Optional<Movie> optionalMovie = movieDAO.findById(id);
        if (optionalMovie.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Movie not found");
        }
        securityManager.throwIfUserHasNotAccessToResource(optionalMovie.get().getCreatedBy());
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

    private Movie uploadToEntity(UploadMovie uploadMovie, Movie movie) {
        modelMapper.map(uploadMovie, movie);
        if (uploadMovie.getCoordinates() != null) {
            Coordinates coordinates = modelMapper.map(
                    uploadMovie.getCoordinates(),
                    Coordinates.class
            );
            movie.setCoordinates(coordinates);
        }
        if (uploadMovie.getDirector() != null) {
            Person director = modelMapper.map(
                    uploadMovie.getDirector(),
                    Person.class
            );
            movie.setDirector(director);
        }
        if (uploadMovie.getScreenwriter() != null) {
            Person screenwriter = modelMapper.map(
                    uploadMovie.getScreenwriter(),
                    Person.class
            );
            movie.setScreenwriter(screenwriter);
        }
        if (uploadMovie.getOperator() != null) {
            Person operator = modelMapper.map(
                    uploadMovie.getOperator(),
                    Person.class
            );
            movie.setOperator(operator);
        }
        return movie;
    }

    private void dtoToEntity(MovieRequest movieDTO, Movie movie) {
        if (movieDTO.getCoordinates() != null) {
            Coordinates coordinates = modelMapper.map(
                    coordinatesService.getCoordinates(movieDTO.getCoordinates()),
                    Coordinates.class
            );
            movie.setCoordinates(coordinates);
        }
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

    private List<Long> persistMovies(List<Movie> mappedEntities, String fileUrl) {
        List<Long> ids = movieDAO.uploadAll(mappedEntities);
        importService.persist(new ImportRequest(true, mappedEntities.size(), fileUrl));
        return ids;
    }
}

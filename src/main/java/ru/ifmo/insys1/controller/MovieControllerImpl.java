package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Pair;
import ru.ifmo.insys1.api.MovieController;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.request.FileUploadForm;
import ru.ifmo.insys1.request.ImportRequest;
import ru.ifmo.insys1.request.MovieRequest;
import ru.ifmo.insys1.request.upload.UploadMovie;
import ru.ifmo.insys1.request.upload.reader.MovieRequestFileReader;
import ru.ifmo.insys1.response.CountResponse;
import ru.ifmo.insys1.service.ImportService;
import ru.ifmo.insys1.service.MinIOMovieService;
import ru.ifmo.insys1.service.MovieService;

import java.util.List;

@ApplicationScoped
@Slf4j
public class MovieControllerImpl implements MovieController {

    @Inject
    private MovieService movieService;

    @Inject
    private MinIOMovieService minIOMovieService;

    @Inject
    private MovieRequestFileReader movieRequestFileReader;

    @Inject
    private ImportService importService;

    @Override
    public Response getMovie(Long id) {
        return Response.ok(movieService.getMovie(id))
                .build();
    }

    @Override
    public Response getAllMovies(int page, int size, String filter, String filterColumn, String sorted) {
        var movies = movieService.getAllMovies(page, size, filter, filterColumn, sorted);

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

    @Override
    public Response deleteMovieByTagline(String tagline) {
        movieService.deleteMovieByTagline(tagline);

        return Response.status(Response.Status.NO_CONTENT)
                .build();
    }

    @Override
    public Response getCountMoviesWithTagline(String tagline) {
        Long count = movieService.getCountMoviesWithTagline(tagline);

        return Response.ok(new CountResponse(count))
                .build();
    }

    @Override
    public Response getCountMoviesWithGenre(MovieGenre movieGenre) {
        Long count = movieService.getCountMoviesWithGenre(movieGenre);

        return Response.ok(new CountResponse(count))
                .build();
    }


    @Override
    public Response incrementOscarsCountForAllMoviesWithRCategory() {
        movieService.incrementOscarsCountForAllMoviesWithRCategory();

        return Response.ok()
                .build();
    }

    @Override
    @Transactional
    public Response upload(FileUploadForm form) {
        Pair<String, String> fileUrl = null;
        try {
            List<UploadMovie> parsedMovies = movieRequestFileReader.read(form);
            fileUrl = minIOMovieService.saveImportFile(form);
            List<Long> savedIds = movieService.uploadAll(parsedMovies, fileUrl);
            log.info("Successfully uploaded movies");
            return Response.status(Response.Status.CREATED)
                    .entity(savedIds)
                    .build();
        } catch (RuntimeException e) {
            log.error("Error while uploading movies, save failed import", e);
            if (fileUrl != null) {
                minIOMovieService.deleteImportFile(fileUrl.getLeft());
            }
            importService.persistInNewTransaction(new ImportRequest(false, 0, null));
            throw e;
        }
    }
}

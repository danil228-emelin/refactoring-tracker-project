package ru.ifmo.insys1.request.upload.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.FileUploadForm;
import ru.ifmo.insys1.request.upload.UploadMovie;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@ApplicationScoped
public class MovieRequestFileReaderImpl implements MovieRequestFileReader {

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private Validator validator;

    @Override
    public List<UploadMovie> read(FileUploadForm form) throws ServiceException, ConstraintViolationException {
        if (form.getFile() == null) {
            throw new ServiceException(
                    BAD_REQUEST,
                    "File is missing"
            );
        }

        try (InputStream inputStream = form.getFile()) {
            List<UploadMovie> movies = objectMapper.readValue(
                    inputStream,
                    objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, UploadMovie.class)
            );

            validateRequest(movies);

            return movies;
        } catch (IOException e) {
            throw new ServiceException(
                    BAD_REQUEST,
                    "Wrong file structure"
            );
        }
    }

    private void validateRequest(List<UploadMovie> movies) throws ConstraintViolationException {
        movies.forEach(this::validateMovie);
    }

    private void validateMovie(UploadMovie movie) {
        var movieViolations = validator.validate(movie);

        if (!movieViolations.isEmpty()) {
            throw new ConstraintViolationException(movieViolations);
        }
    }
}

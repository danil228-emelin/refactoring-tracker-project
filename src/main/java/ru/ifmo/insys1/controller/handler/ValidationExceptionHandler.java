package ru.ifmo.insys1.controller.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.response.HttpErrorResponse;

import java.util.Map;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
@Slf4j
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        log.error("Validation failed: {}", exception.getMessage(), exception);

        Map<String, String> fieldErrors = exception.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> {
                            String path = violation.getPropertyPath()
                                    .toString();
                            int lastDotIndex = path.lastIndexOf(".");

                            return path.substring(lastDotIndex + 1);
                        },
                        ConstraintViolation::getMessage
                ));

        HttpErrorResponse errorResponse = new HttpErrorResponse(
                BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                fieldErrors
        );

        return Response.status(BAD_REQUEST)
                .entity(errorResponse)
                .build();
    }
}


package ru.ifmo.insys1.controller.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.response.HttpErrorResponse;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
@Slf4j
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        log.error(e.getMessage(), e);

        Response.Status internalServerError = INTERNAL_SERVER_ERROR;

        var errorResponse = new HttpErrorResponse(
                internalServerError.getReasonPhrase(),
                e.getMessage()
        );

        return Response.status(internalServerError)
                .entity(errorResponse)
                .build();
    }
}

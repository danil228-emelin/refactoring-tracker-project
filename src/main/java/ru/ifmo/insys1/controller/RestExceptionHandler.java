package ru.ifmo.insys1.controller;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
@Slf4j
public class RestExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        log.error(e.getMessage(), e);

        return Response.status(INTERNAL_SERVER_ERROR)
                .entity(e.getMessage())
                .build();
    }
}

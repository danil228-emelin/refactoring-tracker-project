package ru.ifmo.insys1.controller.handler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.response.HttpErrorResponse;

@Provider
@Slf4j
public class ServiceExceptionHandler implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException e) {
        log.error(e.getMessage(), e);

        var status = e.getStatus();

        var errorResponse = new HttpErrorResponse(
                status.getReasonPhrase(),
                e.getMessage()
        );

        return Response.status(e.getStatus())
                .entity(errorResponse)
                .build();
    }
}

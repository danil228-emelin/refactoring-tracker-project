package ru.ifmo.insys1.exception;

import lombok.Getter;

import static jakarta.ws.rs.core.Response.Status;

@Getter
public class ServiceException extends RuntimeException {

    private final Status status;

    public ServiceException(Status status, String message) {
        super(message);
        this.status = status;
    }
}

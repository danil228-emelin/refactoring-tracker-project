package ru.ifmo.insys1.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class HttpErrorResponse {

    private Map<String, String> details;

    private String reasonPhrase;

    private String message;

    public HttpErrorResponse(String reasonPhrase, String message, Map<String, String> details) {
        this(reasonPhrase, message);
        this.details = details;
    }

    public HttpErrorResponse(String reasonPhrase, String message) {
        this.reasonPhrase = reasonPhrase;
        this.message = message;
    }
}

package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.AuthenticationController;
import ru.ifmo.insys1.request.AuthenticationRequest;
import ru.ifmo.insys1.response.AuthenticationResponse;
import ru.ifmo.insys1.service.AuthenticationService;

@ApplicationScoped
@Tag(name = "Authentication", description = "Аутентификация пользователей")

public class AuthenticationControllerImpl implements AuthenticationController {

    @Inject
    private AuthenticationService authenticationService;

    @Override
    @Operation(
        summary = "Войти в систему",
        description = "Производит аутентификацию пользователя по логину и паролю."
    )
    @APIResponse(responseCode = "200", description = "Успешная аутентификация", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class)))
    @APIResponse(responseCode = "401", description = "Неверные учётные данные")
    public Response authenticate(AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);

        return Response.ok(response).build();
    }
}

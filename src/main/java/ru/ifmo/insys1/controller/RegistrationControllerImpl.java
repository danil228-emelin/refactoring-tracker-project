package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.RegistrationController;
import ru.ifmo.insys1.request.RegistrationRequest;
import ru.ifmo.insys1.response.RegistrationResponse;
import ru.ifmo.insys1.service.RegistrationService;

import static jakarta.ws.rs.core.Response.Status.CREATED;

@ApplicationScoped
@Tag(name = "Registration", description = "Регистрация новых пользователей")
public class RegistrationControllerImpl implements RegistrationController {

    @Inject
    private RegistrationService registrationService;

    @Override
    @Operation(
        summary = "Зарегистрировать нового пользователя",
        description = "Создаёт нового пользователя в системе."
    )
    @APIResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = RegistrationResponse.class)))
    @APIResponse(responseCode = "400", description = "Некорректные данные (логин уже занят и т.п.)")
    public Response register(RegistrationRequest registrationRequest) {
        RegistrationResponse register = registrationService.register(registrationRequest);

        return Response.status(CREATED)
                .entity(register)
                .build();
    }

}

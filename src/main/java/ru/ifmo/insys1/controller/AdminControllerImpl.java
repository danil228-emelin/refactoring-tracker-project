package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.ifmo.insys1.api.AdminController;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.response.ApplicationDTO;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.AdminService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static ru.ifmo.insys1.constants.RoleConstant.ADMIN;

@ApplicationScoped
@Tag(name = "Admin", description = "Управление заявками на роль администратора")
public class AdminControllerImpl implements AdminController {

    @Inject
    private SecurityManager securityManager;

    @Inject
    private AdminService adminService;

    @Override
    @Operation(
        summary = "Подать заявку на роль администратора",
        description = "Позволяет зарегистрированному пользователю подать заявку на получение прав администратора."
    )
    @APIResponse(responseCode = "201", description = "Заявка успешно подана")
    @APIResponse(responseCode = "400", description = "Пользователь уже является администратором")
    @APIResponse(responseCode = "401", description = "Необходимо войти в систему")
    public Response submitApplication() {
        if (securityManager.hasAnyRole(ADMIN)) {
            throw new ServiceException(
                    BAD_REQUEST,
                    "You are already an administrator"
            );
        }

        adminService.submitApplication();

        return Response.status(CREATED)
                .build();
    }

    @Override
     @Operation(
        summary = "Принять заявку на администрирование",
        description = "Администратор одобряет заявку пользователя."
    )
    @APIResponse(responseCode = "201", description = "Заявка принята")
    @APIResponse(responseCode = "403", description = "Только администраторы могут принимать заявки")
    public Response acceptApplication(ApplicationDTO applicationDTO) {
        if (!securityManager.hasAnyRole(ADMIN)) {
            securityManager.throwForbiddenException();
        }

        adminService.acceptApplication(applicationDTO);

        return Response.status(CREATED)
                .build();
    }

    @Override
    @Operation(
        summary = "Получить список всех заявок",
        description = "Возвращает пагинированный список заявок на администрирование."
    )
    @APIResponse(responseCode = "200", description = "Список заявок", content = @Content(schema = @Schema(implementation = ApplicationDTO.class)))
    @APIResponse(responseCode = "403", description = "Требуются права администратора")
    public Response getApplications(int page, int size) {
        if (!securityManager.hasAnyRole(ADMIN)) {
            securityManager.throwForbiddenException();
        }

        List<ApplicationDTO> responses = adminService.getAllApplications(page, size);

        return Response.ok(responses)
                .build();
    }
}

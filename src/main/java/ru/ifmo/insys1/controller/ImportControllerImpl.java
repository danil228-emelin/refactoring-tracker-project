package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.ImportController;
import ru.ifmo.insys1.response.ImportResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.ImportService;

import java.util.List;

import static ru.ifmo.insys1.constants.RoleConstant.ADMIN;

@ApplicationScoped
@Tag(name = "Imports", description = "Просмотр импортов данных")
public class ImportControllerImpl implements ImportController {

    @Inject
    private SecurityManager securityManager;

    @Inject
    private ImportService importService;

    @Override
     @Operation(
        summary = "Получить список импортов",
        description = "Администраторы видят все импорты, обычные пользователи — только свои."
    )
    @APIResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ImportResponse.class))))
    public Response getAll() {
        List<ImportResponse> models = importService.getAll()
                .stream()
                .filter(this::filterImport)
                .toList();

        return Response.ok(models)
                .build();
    }

    private boolean filterImport(ImportResponse importModel) {
        if (securityManager.hasAnyRole(ADMIN)) {
            return true;
        }

        return securityManager.getCallerPrincipal()
                .equals(importModel.getCreatedBy());
    }
}

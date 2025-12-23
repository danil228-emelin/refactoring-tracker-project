package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
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

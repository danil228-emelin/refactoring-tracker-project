package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
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
public class AdminControllerImpl implements AdminController {

    @Inject
    private SecurityManager securityManager;

    @Inject
    private AdminService adminService;

    @Override
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
    public Response acceptApplication(ApplicationDTO applicationDTO) {
        if (!securityManager.hasAnyRole(ADMIN)) {
            securityManager.throwForbiddenException();
        }

        adminService.acceptApplication(applicationDTO);

        return Response.status(CREATED)
                .build();
    }

    @Override
    public Response getApplications(int page, int size) {
        if (!securityManager.hasAnyRole(ADMIN)) {
            securityManager.throwForbiddenException();
        }

        List<ApplicationDTO> responses = adminService.getAllApplications(page, size);

        return Response.ok(responses)
                .build();
    }
}

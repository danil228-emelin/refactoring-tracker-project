package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.LabelsController;
import ru.ifmo.insys1.service.LabelsService;

@ApplicationScoped
public class LabelsControllerImpl implements LabelsController {

    @Inject
    private LabelsService labelsService;

    @Override
    public Response getLabel(Integer id) {
        return Response.ok(labelsService.getLabel(id))
                .build();
    }

    @Override
    public Response createLabel() {
        return Response.status(Response.Status.CREATED)
                .entity(labelsService.createLabel())
                .build();
    }
}

package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.CoordinatesController;
import ru.ifmo.insys1.dto.CoordinatesDTO;
import ru.ifmo.insys1.response.GetAllCoordinates;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.CoordinatesService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.*;

@ApplicationScoped
public class CoordinatesControllerImpl implements CoordinatesController {

    @Inject
    private SecurityManager securityManager;

    @Inject
    private CoordinatesService coordinatesService;

    @Override
    public Response getCoordinates(Long id) {
        securityManager.throwIfAnonymous();

        var coordinates = coordinatesService.getCoordinates(id);

        return Response.ok(coordinates)
                .build();
    }

    @Override
    public Response getAllCoordinates(int page, int size) {
        securityManager.throwIfAnonymous();

        List<CoordinatesDTO> allCoordinates = coordinatesService.getAllCoordinates(page, size);

        return Response.ok(new GetAllCoordinates(allCoordinates))
                .build();
    }

    @Override
    public Response createCoordinates(CoordinatesDTO coordinates) {
        securityManager.throwIfAnonymous();

        var createCoordinates = coordinatesService.createCoordinates(coordinates);

        return Response.status(CREATED)
                .entity(createCoordinates)
                .build();
    }

    @Override
    public Response updateCoordinates(Long id, CoordinatesDTO coordinates) {
        securityManager.throwIfAnonymous();

        coordinatesService.updateCoordinates(id, coordinates);

        return Response.ok().build();
    }

    @Override
    public Response deleteCoordinates(Long id) {
        securityManager.throwIfAnonymous();

        coordinatesService.deleteCoordinates(id);

        return Response.ok().build();
    }
}

package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.CoordinatesController;
import ru.ifmo.insys1.request.CoordinatesRequest;
import ru.ifmo.insys1.response.CoordinatesResponse;
import ru.ifmo.insys1.service.CoordinatesService;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

@ApplicationScoped
public class CoordinatesControllerImpl implements CoordinatesController {

    @Inject
    private CoordinatesService coordinatesService;

    @Override
    public Response getCoordinates(Long id) {
        var coordinates = coordinatesService.getCoordinates(id);

        return Response.ok(coordinates)
                .build();
    }

    @Override
    public Response getAllCoordinates(int page, int size) {
        List<CoordinatesResponse> allCoordinates = coordinatesService.getAllCoordinates(page, size);

        return Response.ok(allCoordinates)
                .build();
    }

    @Override
    public Response createCoordinates(CoordinatesRequest coordinates) {
        var createdCoordinates = coordinatesService.createCoordinates(coordinates);

        return Response.status(CREATED)
                .entity(createdCoordinates)
                .build();
    }

    @Override
    public Response updateCoordinates(Long id, CoordinatesRequest coordinates) {
        coordinatesService.updateCoordinates(id, coordinates);

        return Response.ok().build();
    }

    @Override
    public Response deleteCoordinates(Long id) {
        coordinatesService.deleteCoordinates(id);

        return Response.status(NO_CONTENT)
                .build();
    }
}

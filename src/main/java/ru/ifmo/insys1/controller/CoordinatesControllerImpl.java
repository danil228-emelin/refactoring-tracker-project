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
@Tag(name = "Coordinates", description = "Управление координатами (X, Y)")
public class CoordinatesControllerImpl implements CoordinatesController {

    @Inject
    private CoordinatesService coordinatesService;

    @Override
    @Operation(summary = "Получить координаты по ID")
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CoordinatesResponse.class)))
    @APIResponse(responseCode = "404", description = "Координаты не найдены")
    public Response getCoordinates(Long id) {
        var coordinates = coordinatesService.getCoordinates(id);

        return Response.ok(coordinates)
                .build();
    }

    @Override
    @Operation(summary = "Получить список координат (пагинация)")   
    @APIResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CoordinatesResponse.class))))
    public Response getAllCoordinates(int page, int size) {
        List<CoordinatesResponse> allCoordinates = coordinatesService.getAllCoordinates(page, size);

        return Response.ok(allCoordinates)
                .build();
    }

    @Override
    @Operation(summary = "Создать новые координаты")
    @APIResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = CoordinatesResponse.class)))
    public Response createCoordinates(CoordinatesRequest coordinates) {
        var createdCoordinates = coordinatesService.createCoordinates(coordinates);

        return Response.status(CREATED)
                .entity(createdCoordinates)
                .build();
    }

    @Override
    @Operation(summary = "Обновить координаты")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Координаты не найдены")
    public Response updateCoordinates(Long id, CoordinatesRequest coordinates) {
        coordinatesService.updateCoordinates(id, coordinates);

        return Response.ok().build();
    }

    @Override
    @Operation(summary = "Удалить координаты")
    @APIResponse(responseCode = "204", description = "Успешно удалено")
    @APIResponse(responseCode = "404", description = "Координаты не найдены")
    public Response deleteCoordinates(Long id) {
        coordinatesService.deleteCoordinates(id);

        return Response.status(NO_CONTENT)
                .build();
    }
}

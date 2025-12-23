package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.LocationController;
import ru.ifmo.insys1.request.LocationRequest;
import ru.ifmo.insys1.service.LocationService;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

@ApplicationScoped
@Tag(name = "Locations", description = "Управление локациями (название, координаты)")
public class LocationControllerImpl implements LocationController {

    @Inject
    private LocationService locationService;

    @Override
    @Operation(summary = "Получить локацию по ID")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Локация не найдена")
    public Response getLocation(Long id) {
        var location = locationService.getLocation(id);

        return Response.ok(location)
                .build();
    }

    @Override
    @Operation(summary = "Получить список локаций (пагинация)")
    @APIResponse(responseCode = "200")
    public Response getAllLocations(int page, int size) {
        var locations = locationService.getAllLocations(page, size);

        return Response.ok(locations)
                .build();
    }

    @Override
    @Operation(summary = "Создать локацию")
    @APIResponse(responseCode = "201")
    public Response createLocation(LocationRequest location) {
        var createdLocation = locationService.createLocation(location);

        return Response.status(CREATED)
                .entity(createdLocation)
                .build();
    }

    @Override
    @Operation(summary = "Обновить локацию")
    @APIResponse(responseCode = "200")
    @APIResponse(responseCode = "404", description = "Локация не найдена")
    public Response updateLocation(Long id, LocationRequest location) {
        locationService.updateLocation(id, location);

        return Response.ok()
                .build();
    }

    @Override
    @Operation(summary = "Удалить локацию")
    @APIResponse(responseCode = "204")
    @APIResponse(responseCode = "404", description = "Локация не найдена")
    public Response deleteLocation(Long id) {
        locationService.deleteLocation(id);

        return Response.status(NO_CONTENT)
                .build();
    }
}

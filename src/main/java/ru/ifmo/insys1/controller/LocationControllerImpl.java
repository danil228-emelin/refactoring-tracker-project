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
public class LocationControllerImpl implements LocationController {

    @Inject
    private LocationService locationService;

    @Override
    public Response getLocation(Long id) {
        var location = locationService.getLocation(id);

        return Response.ok(location)
                .build();
    }

    @Override
    public Response getAllLocations(int page, int size) {
        var locations = locationService.getAllLocations(page, size);

        return Response.ok(locations)
                .build();
    }

    @Override
    public Response createLocation(LocationRequest location) {
        var createdLocation = locationService.createLocation(location);

        return Response.status(CREATED)
                .entity(createdLocation)
                .build();
    }

    @Override
    public Response updateLocation(Long id, LocationRequest location) {
        locationService.updateLocation(id, location);

        return Response.ok()
                .build();
    }

    @Override
    public Response deleteLocation(Long id) {
        locationService.deleteLocation(id);

        return Response.status(NO_CONTENT)
                .build();
    }
}

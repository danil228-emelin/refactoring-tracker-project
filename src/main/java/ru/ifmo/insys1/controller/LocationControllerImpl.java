package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.LocationController;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.LocationDTO;
import ru.ifmo.insys1.service.LocationService;

@ApplicationScoped
public class LocationControllerImpl implements LocationController {

    @Inject
    private LocationService locationService;

    @Override
    public Response getLocation(Integer id) {
        return Response.ok(
                locationService.findById(id)
                        .orElseThrow(() -> new ServiceException(Response.Status.NOT_FOUND, "Location not found"))
        ).build();
    }

    @Override
    public Response getAllLocations() {
        return Response.ok(locationService.findAll())
                .build();
    }

    @Override
    public Response createLocation(LocationDTO location) {
        locationService.save(location);
        return Response.status(Response.Status.CREATED)
                .build();
    }

    @Override
    public Response deleteLocation(Integer id) {
        locationService.delete(id);
        return Response.status(Response.Status.NO_CONTENT)
                .build();
    }
}

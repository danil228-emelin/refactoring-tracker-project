package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.LocationController;
import ru.ifmo.insys1.dto.LocationDTO;
import ru.ifmo.insys1.response.GetAllLocations;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.LocationService;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

@ApplicationScoped
public class LocationControllerImpl implements LocationController {

    @Inject
    private LocationService locationService;

    @Inject
    private SecurityManager securityManager;

    @Override
    public Response getLocation(Long id) {
        securityManager.throwIfAnonymous();

        var location = locationService.getLocation(id);

        return Response.ok(location).build();
    }

    @Override
    public Response getAllLocations(int page, int size) {
        securityManager.throwIfAnonymous();

        var locations = locationService.getAllLocations(page, size);

        return Response.ok(new GetAllLocations(locations)).build();
    }

    @Override
    public Response createLocation(LocationDTO location) {
        securityManager.throwIfAnonymous();

        var createdLocation = locationService.createLocation(location);

        return Response.status(CREATED)
                .entity(createdLocation)
                .build();
    }

    @Override
    public Response updateLocation(Long id, LocationDTO location) {
        securityManager.throwIfAnonymous();

        locationService.updateLocation(id, location);

        return Response.ok()
                .build();
    }

    @Override
    public Response deleteLocation(Long id) {
        securityManager.throwIfAnonymous();

        locationService.deleteLocation(id);

        return Response.status(NO_CONTENT)
                .build();
    }
}

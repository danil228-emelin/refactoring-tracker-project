package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.LocationDAO;
import ru.ifmo.insys1.request.LocationRequest;
import ru.ifmo.insys1.entity.Location;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.response.LocationResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.LocationService;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class LocationServiceImpl implements LocationService {

    @Inject
    private LocationDAO locationDAO;

    @Inject
    private SecurityManager securityManager;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public LocationResponse getLocation(Long id) {
        Optional<Location> optionalLocation = locationDAO.findById(id);

        if (optionalLocation.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Location not found");
        }

        return modelMapper.map(optionalLocation.get(), LocationResponse.class);
    }

    @Override
    public List<LocationResponse> getAllLocations(int page, int size) {
        List<Location> allLocations = locationDAO.findAll(page, size);

        return allLocations.stream()
                .map(l -> modelMapper.map(l, LocationResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public LocationResponse createLocation(LocationRequest locationRequest) {
        Location location = modelMapper.map(locationRequest, Location.class);

        locationDAO.save(location);

        return modelMapper.map(location, LocationResponse.class);
    }

    @Override
    @Transactional
    public void updateLocation(Long id, LocationRequest locationRequest) {
        Optional<Location> optionalLocation = locationDAO.findById(id);

        if (optionalLocation.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Location not found");
        }

        Location location = optionalLocation.get();

        securityManager.throwIfUserHasNotAccessToResource(location.getCreatedBy());

        modelMapper.map(locationRequest, location);

        locationDAO.update(location);
    }

    @Override
    @Transactional
    public void deleteLocation(Long id) {
        Optional<Location> optionalLocation = locationDAO.findById(id);

        if (optionalLocation.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Location not found");
        }

        Location location = optionalLocation.get();

        securityManager.throwIfUserHasNotAccessToResource(location.getCreatedBy());

        locationDAO.delete(id);
    }

}

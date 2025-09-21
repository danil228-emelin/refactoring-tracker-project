package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.LocationRequest;
import ru.ifmo.insys1.response.LocationResponse;

import java.util.List;

public interface LocationService {

    LocationResponse getLocation(Long id);

    List<LocationResponse> getAllLocations(int page, int size);

    LocationResponse createLocation(LocationRequest coordinates);

    void updateLocation(Long id, LocationRequest coordinates);

    void deleteLocation(Long id);
}

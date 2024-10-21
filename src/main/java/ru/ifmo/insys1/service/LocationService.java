package ru.ifmo.insys1.service;

import ru.ifmo.insys1.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    LocationDTO getLocation(Long id);

    List<LocationDTO> getAllLocations(int page, int size);

    LocationDTO createLocation(LocationDTO coordinates);

    void updateLocation(Long id, LocationDTO coordinates);

    void deleteLocation(Long id);
}

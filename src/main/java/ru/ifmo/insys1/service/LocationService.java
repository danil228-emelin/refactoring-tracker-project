package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.LocationDTO;
import ru.ifmo.insys1.response.LocationResponse;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Optional<LocationResponse> findById(Integer id);

    List<LocationResponse> findAll();

    void save(LocationDTO location);

    void delete(Integer id);
}

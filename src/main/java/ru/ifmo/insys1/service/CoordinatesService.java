package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.CoordinatesRequest;
import ru.ifmo.insys1.response.CoordinatesResponse;

import java.util.List;

public interface CoordinatesService {

    CoordinatesResponse getCoordinates(Long id);

    List<CoordinatesResponse> getAllCoordinates(int page, int size);

    CoordinatesResponse createCoordinates(CoordinatesRequest coordinates);

    void updateCoordinates(Long id, CoordinatesRequest coordinates);

    void deleteCoordinates(Long id);
}

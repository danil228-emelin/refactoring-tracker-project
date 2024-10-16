package ru.ifmo.insys1.service;

import ru.ifmo.insys1.dto.CoordinatesDTO;

import java.util.List;

public interface CoordinatesService {

    CoordinatesDTO getCoordinates(Long id);

    List<CoordinatesDTO> getAllCoordinates(int page, int size);

    CoordinatesDTO createCoordinates(CoordinatesDTO coordinates);

    void updateCoordinates(Long id, CoordinatesDTO coordinates);

    void deleteCoordinates(Long id);
}

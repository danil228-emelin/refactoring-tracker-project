package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.CreateCargoDTO;
import ru.ifmo.insys1.response.CargoResponse;

import java.util.Optional;

public interface CargoesService {

    Optional<CargoResponse> getBySscc(String sscc);

    void save(CreateCargoDTO request);
}

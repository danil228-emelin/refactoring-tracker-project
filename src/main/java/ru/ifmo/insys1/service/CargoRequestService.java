package ru.ifmo.insys1.service;

import ru.ifmo.insys1.request.CargoRequestDTO;
import ru.ifmo.insys1.response.CargoRequestResponse;

import java.util.List;

public interface CargoRequestService {

    List<CargoRequestResponse> getCargoRequestByClient(String username);

    List<CargoRequestResponse> getAllCargoRequests();

    void createCargoRequest(CargoRequestDTO dto);

    void deleteCargoRequest(Integer id);
}

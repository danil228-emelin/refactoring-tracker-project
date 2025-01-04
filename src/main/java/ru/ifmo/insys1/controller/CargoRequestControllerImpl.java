package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.CargoRequestController;
import ru.ifmo.insys1.request.CargoRequestDTO;
import ru.ifmo.insys1.service.CargoRequestService;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

@ApplicationScoped
public class CargoRequestControllerImpl implements CargoRequestController {

    @Inject
    private CargoRequestService cargoRequestService;

    @Override
    public Response getCargoRequestByClientName(String clientName) {
        var location = cargoRequestService.getCargoRequestByClient(clientName);
        return Response.ok(location)
                .build();
    }

    @Override
    public Response getAllCargoRequests() {
        var locations = cargoRequestService.getAllCargoRequests();
        return Response.ok(locations)
                .build();
    }

    @Override
    public Response createCargoRequest(CargoRequestDTO cargoRequestDTO) {
        cargoRequestService.createCargoRequest(cargoRequestDTO);
        return Response.status(CREATED)
                .build();
    }

    @Override
    public Response deleteCargoRequest(Integer id) {
        cargoRequestService.deleteCargoRequest(id);
        return Response.status(NO_CONTENT)
                .build();
    }
}

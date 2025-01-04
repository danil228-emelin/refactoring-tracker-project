package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.CargoesController;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.CreateCargoDTO;
import ru.ifmo.insys1.response.CargoResponse;
import ru.ifmo.insys1.service.CargoesService;

import java.util.Optional;

@ApplicationScoped
public class CargoesControllerImpl implements CargoesController {

    @Inject
    private CargoesService cargoesService;

    @Override
    public Response getCargoBySscc(String sscc) {
        Optional<CargoResponse> cargoResponse = cargoesService.getBySscc(sscc);
        if (cargoResponse.isEmpty()) {
            throw new ServiceException(Response.Status.NOT_FOUND, "Cargo by SSCC not found");
        }
        return Response.ok(cargoResponse.get())
                .build();
    }

    @Override
    public Response createCargo(CreateCargoDTO request) {
        cargoesService.save(request);
        return Response.status(Response.Status.CREATED)
                .build();
    }
}

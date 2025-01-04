package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.entity.Cargo;
import ru.ifmo.insys1.entity.CargoStatus;
import ru.ifmo.insys1.entity.Location;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.UpdateCargoStatus;

import java.util.Optional;

@ApplicationScoped
public class CargoStatusDAO {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CargoDAO cargoDAO;

    @Inject
    private LocationDAO locationDAO;

    @Transactional
    public void update(String ssccCode, UpdateCargoStatus updateCargoStatus) {
        Optional<Cargo> optionalCargo = cargoDAO.getBySscc(ssccCode);
        if (optionalCargo.isEmpty()) {
            throw new ServiceException(Response.Status.NOT_FOUND, "Cargo with SSC code " + ssccCode + " not found");
        }
        CargoStatus cargoStatus = optionalCargo.get().getCargoStatus();
        if (updateCargoStatus.getCargoStatusDelivery() != null) {
            cargoStatus.setCargoStatusDelivery(updateCargoStatus.getCargoStatusDelivery());
        }
        if (updateCargoStatus.getLocationId() != null) {
            Optional<Location> locationById = locationDAO.findById(updateCargoStatus.getLocationId());
            if (locationById.isEmpty()) {
                throw new ServiceException(Response.Status.NOT_FOUND, "Location not found");
            }
            cargoStatus.setLocation(locationById.get());
        }
    }
}

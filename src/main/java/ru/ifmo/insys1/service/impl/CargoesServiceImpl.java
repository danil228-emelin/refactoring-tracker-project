package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.dao.CargoDAO;
import ru.ifmo.insys1.entity.Cargo;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.CreateCargoDTO;
import ru.ifmo.insys1.response.CargoResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.CargoesService;
import ru.ifmo.insys1.service.NotificationsService;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static ru.ifmo.insys1.constants.RoleConstant.CLIENT;
import static ru.ifmo.insys1.constants.RoleConstant.OPERATOR;

@ApplicationScoped
public class CargoesServiceImpl implements CargoesService {

    @Inject
    private CargoDAO cargoDAO;

    @Inject
    private SecurityManager securityManager;

    @Override
    public Optional<CargoResponse> getBySscc(String sscc) {
        return cargoDAO.getBySscc(sscc)
                .map(cargo -> {
                    checkIsOwner(cargo);
                    return mapToResponse(cargo);
                });
    }

    private void checkIsOwner(Cargo cargo) {
        if (securityManager.hasAnyRole(CLIENT)) {
            Integer ownerId = cargo.getOrder().getClient().getId();
            if (!ownerId.equals(securityManager.getCallerPrincipal())) {
                throw new ServiceException(Response.Status.FORBIDDEN, "You are not an owner");
            }
        }
    }

    private CargoResponse mapToResponse(Cargo cargo) {
        CargoResponse response = new CargoResponse();
        response.setId(cargo.getId());
        response.setName(cargo.getName());
        response.setDestinationCenter(cargo.getDestinationCenter().getName());
        response.setReceptionCenter(cargo.getReceptionCenter().getName());
        response.setCargoType(cargo.getCargoType());
        response.setRegistrationDate(cargo.getRegistrationDate().format(DateTimeFormatter.ISO_DATE));
        response.setCurrentLocation(cargo.getCargoStatus().getLocation().getName());
        response.setCurrentLocationType(cargo.getCargoStatus().getLocation().getType());
        response.setCargoStatusDelivery(cargo.getCargoStatus().getCargoStatusDelivery());
        response.setWeight(cargo.getWeight());
        if (cargo.getLabel() != null) {
            response.setSsccCode(cargo.getLabel().getSsccCode());
        }
        if (cargo.getOrder() != null) {
            if (response.getDeliveryDate() != null) {
                response.setDeliveryDate(
                        cargo.getOrder()
                                .getDeliveryDate()
                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                );
            }
            if (cargo.getOrder().getClient() != null) {
                response.setClientUsername(cargo.getOrder().getClient().getUsername());
            }
        }
        return response;
    }

    @Override
    @Transactional
    public void save(CreateCargoDTO request) {
        if (!securityManager.hasAnyRole(OPERATOR)) {
            throw new ServiceException(Response.Status.FORBIDDEN, "You do not have permission to perform this operation");
        }
        cargoDAO.save(request);
    }
}

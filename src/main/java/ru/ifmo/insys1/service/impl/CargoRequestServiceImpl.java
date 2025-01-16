package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.CargoRequestDAO;
import ru.ifmo.insys1.entity.CargoRequest;
import ru.ifmo.insys1.entity.Location;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.CargoRequestDTO;
import ru.ifmo.insys1.response.CargoRequestResponse;
import ru.ifmo.insys1.response.LocationResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.CargoRequestService;
import ru.ifmo.insys1.service.LocationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.*;
import static ru.ifmo.insys1.constants.RoleConstant.*;

@ApplicationScoped
public class CargoRequestServiceImpl implements CargoRequestService {

    @Inject
    private LocationService locationService;

    @Inject
    private CargoRequestDAO cargoRequestDAO;

    @Inject
    private SecurityManager securityManager;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public List<CargoRequestResponse> getCargoRequestByClient(String clientName) {
       // if (!securityManager.hasAnyRole(MANAGER, OPERATOR)) {
       //     throw new ServiceException(FORBIDDEN, "You don't have access. Only Manager or Operator");
        //}
        List<CargoRequest> result = cargoRequestDAO.findByClientName(clientName);
        return result.stream()
                .map(this::mapToModel)
                .toList();
    }

    @Override
    public List<CargoRequestResponse> getAllCargoRequests() {
        if (!securityManager.hasAnyRole(CLIENT)) {
            throw new ServiceException(FORBIDDEN, "You are not a client");
        }
        List<CargoRequest> allLocations = cargoRequestDAO.findByOwner(securityManager.getCallerPrincipal());
        return allLocations.stream()
                .map(this::mapToModel)
                .toList();
    }

    @Override
    @Transactional
    public void createCargoRequest(CargoRequestDTO dto) {
        if (!securityManager.hasAnyRole(CLIENT)) {
            throw new ServiceException(FORBIDDEN, "You are not a client");
        }
        CargoRequest cargoRequest = mapToEntity(dto);
        cargoRequestDAO.save(cargoRequest);
    }

    @Override
    @Transactional
    public void deleteCargoRequest(Integer id) {
        Optional<CargoRequest> optionalCargoRequest = cargoRequestDAO.findById(id);
        if (optionalCargoRequest.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Cargo request not found");
        }
        cargoRequestDAO.delete(id);
    }

    private CargoRequestResponse mapToModel(CargoRequest entity) {
        var model = new CargoRequestResponse();
        model.setId(entity.getId());
        model.setName(entity.getName());
        LocalDateTime creationDate = entity.getCreationDate();
        if (creationDate != null) {
            model.setCreationDate(creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        model.setCargoType(entity.getCargoType());
        model.setReceptionCenterId(entity.getReceptionCenter().getId());
        model.setDestinationCenterId(entity.getDestinationCenter().getId());
        return model;
    }

    private CargoRequest mapToEntity(CargoRequestDTO dto) {
        Optional<LocationResponse> source = locationService.findById(dto.getReceptionCenter());
        Optional<LocationResponse> destination = locationService.findById(dto.getDestinationCenter());
        if (source.isEmpty()) {
            throw new ServiceException(BAD_REQUEST, "Reception center must be not null");
        }
        if (destination.isEmpty()) {
            throw new ServiceException(BAD_REQUEST, "Destination center must be not null");
        }
        CargoRequest cargoRequest = new CargoRequest();
        cargoRequest.setName(dto.getName());
        cargoRequest.setCargoType(dto.getCargoType());
        cargoRequest.setOwnerOid(securityManager.getCallerPrincipal());
        cargoRequest.setReceptionCenter(modelMapper.map(source.get(), Location.class));
        cargoRequest.setDestinationCenter(modelMapper.map(destination.get(), Location.class));
        return cargoRequest;
    }
}

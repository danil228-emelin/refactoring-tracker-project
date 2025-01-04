package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.IncidentsController;
import ru.ifmo.insys1.constants.RoleConstant;
import ru.ifmo.insys1.dao.CargoDAO;
import ru.ifmo.insys1.dao.IncidentDAO;
import ru.ifmo.insys1.dao.UserDAO;
import ru.ifmo.insys1.entity.Cargo;
import ru.ifmo.insys1.entity.Incident;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.IncidentRequest;
import ru.ifmo.insys1.response.IncidentResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.NotificationsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ApplicationScoped
public class IncidentsControllerImpl implements IncidentsController {

    @Inject
    private IncidentDAO incidentDAO;

    @Inject
    private SecurityManager securityManager;

    @Inject
    private CargoDAO cargoDAO;

    @Inject
    private NotificationsService notificationService;

    @Inject
    private UserDAO userDAO;

    @Override
    public Response getIncidents(Integer clientId) {
        if (!securityManager.hasAnyRole(RoleConstant.MANAGER)) {
            throw new ServiceException(Response.Status.FORBIDDEN, "You do not have the required role MANAGER");
        }
        return Response.ok(incidentDAO.getByClientId(clientId)
                .stream()
                .map(this::mapToModel)
                .toList()).build();
    }

    private IncidentResponse mapToModel(Incident incident) {
        IncidentResponse response = new IncidentResponse();
        response.setIncidentDate(incident.getOccurrenceDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setSscc(incident.getCargo().getLabel().getSsccCode());
        response.setIncidentType(incident.getType());
        response.setClientUsername(incident.getCargo().getOrder().getClient().getUsername());
        response.setDescription(incident.getDescription());
        return response;
    }

    @Override
    @Transactional
    public Response createIncident(IncidentRequest incidentRequest) {
        incidentDAO.save(mapToEntity(incidentRequest));
        return Response.status(Response.Status.CREATED).build();
    }

    private Incident mapToEntity(IncidentRequest incidentRequest) {
        Incident incident = new Incident();
        var optionalCargo = cargoDAO.getBySscc(incidentRequest.getSsccCode());
        if (optionalCargo.isEmpty()) {
            throw new ServiceException(Response.Status.BAD_REQUEST, "Cargo not found");
        }
        incident.setCargo(optionalCargo.get());
        incident.setDescription(incidentRequest.getDescription());
        incident.setOccurrenceDate(LocalDateTime.now());
        incident.setType(incidentRequest.getType());
        saveNotifications(incidentRequest, optionalCargo);
        return incident;
    }

    private void saveNotifications(IncidentRequest incidentRequest, Optional<Cargo> optionalCargo) {
        notificationService.save(
                String.format(
                        "Incident: type - %s; cargo sscc-code - %s; message - %s",
                        incidentRequest.getType().toString(),
                        incidentRequest.getSsccCode(),
                        incidentRequest.getDescription()
                ),
                optionalCargo.get().getOrder().getClient().getId(),
                false
        );
        userDAO.findManagers()
                .forEach(manager -> notificationService.save(
                        String.format(
                                "Incident: type - %s; cargo sscc-code - %s; message - %s",
                                incidentRequest.getType().toString(),
                                incidentRequest.getSsccCode(),
                                incidentRequest.getDescription()
                        ),
                        manager.getId(),
                        false
                ));
    }
}

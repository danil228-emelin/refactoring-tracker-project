package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.api.CargoStatusController;
import ru.ifmo.insys1.dao.CargoDAO;
import ru.ifmo.insys1.dao.CargoStatusDAO;
import ru.ifmo.insys1.dao.IncidentDAO;
import ru.ifmo.insys1.entity.Cargo;
import ru.ifmo.insys1.entity.Incident;
import ru.ifmo.insys1.entity.IncidentType;
import ru.ifmo.insys1.request.UpdateCargoStatus;
import ru.ifmo.insys1.response.CargoResponse;
import ru.ifmo.insys1.service.CargoesService;
import ru.ifmo.insys1.service.NotificationsService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ApplicationScoped
@Slf4j
public class CargoStatusControllerImpl implements CargoStatusController {

    @Inject
    private CargoStatusDAO cargoStatusDAO;

    @Inject
    private CargoesService cargoesService;

    @Inject
    private CargoDAO cargoDAO;

    @Inject
    private IncidentDAO incidentDAO;

    @Inject
    private NotificationsService notificationsService;

    @Override
    public Response updateCargoStatus(String ssccCode, UpdateCargoStatus updateCargoStatus) {
        cargoStatusDAO.update(ssccCode, updateCargoStatus);
        CargoResponse bySscc = cargoesService.getBySscc(ssccCode).get();
        if (bySscc.getDeliveryDate() != null) {
            var deliveryDate = BigDecimal.valueOf(
                    LocalDateTime.parse(bySscc.getDeliveryDate()).toEpochSecond(ZoneOffset.UTC)
            );
            var now = BigDecimal.valueOf(
                    LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
            );
            if (now.compareTo(deliveryDate) > 0) {
                var percent = now.divide(deliveryDate, 10, RoundingMode.HALF_UP)
                        .subtract(BigDecimal.ONE)
                        .multiply(BigDecimal.valueOf(100));
                log.warn("Delay by percent: {}; sscc-code: {}", Double.parseDouble(percent.toString()), bySscc.getSsccCode());
                if (percent.compareTo(BigDecimal.valueOf(10)) >= 0) {
                    log.warn("Dangerous delay, more than 10 percent");
                    Incident incident = new Incident();
                    incident.setType(IncidentType.DELAY);
                    incident.setOccurrenceDate(LocalDateTime.now());
                    Cargo cargo = cargoDAO.getBySscc(ssccCode).get();
                    incident.setCargo(cargo);
                    incident.setDescription("Delay! Delivery date is" + bySscc.getDeliveryDate());
                    incidentDAO.save(incident);
                    notificationsService.save(
                            "Delay! Delivery date is" + bySscc.getDeliveryDate(),
                            cargo.getOrder().getClient().getId(),
                            false
                    );
                }
            }
        }
        return Response.ok(bySscc).build();
    }
}

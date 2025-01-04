package ru.ifmo.insys1.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.api.NotificationsController;
import ru.ifmo.insys1.response.NotificationResponse;
import ru.ifmo.insys1.service.NotificationsService;

import java.util.List;

@ApplicationScoped
public class NotificationsControllerImpl implements NotificationsController {

    @Inject
    private NotificationsService notificationsService;

    @Override
    public Response getNotifications(Integer ownerId) {
        List<NotificationResponse> notificationResponses = notificationsService.getByOwnerId(ownerId);
        notificationsService.deleteAllByOwnerId(ownerId);
        return Response.ok().entity(notificationResponses).build();
    }
}

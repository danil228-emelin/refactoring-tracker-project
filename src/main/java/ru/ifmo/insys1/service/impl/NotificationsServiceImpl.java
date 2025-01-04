package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.NotificationDAO;
import ru.ifmo.insys1.entity.Notification;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.response.NotificationResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.NotificationsService;

import java.util.List;

@ApplicationScoped
public class NotificationsServiceImpl implements NotificationsService {

    @Inject
    private NotificationDAO notificationDAO;

    @Inject
    private ModelMapper modelMapper;

    @Inject
    private SecurityManager securityManager;

    @Override
    @Transactional
    public void deleteAllByOwnerId(Integer ownerId) {
        notificationDAO.deleteAllByOwnerId(ownerId);
    }

    @Override
    public List<NotificationResponse> getByOwnerId(Integer ownerId) {
        if (!securityManager.getCallerPrincipal().equals(ownerId)) {
            throw new ServiceException(Response.Status.FORBIDDEN, "You are not allowed to access this resource");
        }
        List<Notification> notifications = notificationDAO.getAllByOwner(ownerId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public void save(String description, Integer ownerId, Boolean isPositive) {
        var notification = new Notification();
        notification.setDescription(description);
        notification.setIsPositive(isPositive);
        notification.setOwnerOid(ownerId);
        notificationDAO.save(notification);
    }
}

package ru.ifmo.insys1.service;

import ru.ifmo.insys1.response.NotificationResponse;

import java.util.List;

public interface NotificationsService {

    void deleteAllByOwnerId(Integer ownerId);

    List<NotificationResponse> getByOwnerId(Integer ownerId);

    void save(String description, Integer ownerId, Boolean isPositive);
}

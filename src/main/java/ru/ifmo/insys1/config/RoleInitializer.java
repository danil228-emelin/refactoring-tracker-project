package ru.ifmo.insys1.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import ru.ifmo.insys1.config.event.MigrationsCompleted;
import ru.ifmo.insys1.constants.RoleConstant;
import ru.ifmo.insys1.dao.RoleDAO;

@ApplicationScoped
public class RoleInitializer {

    @Inject
    private RoleDAO roleDAO;

    public void initRoles(@Observes MigrationsCompleted event) {
        RoleConstant.ROLES
                .forEach(roleDAO::saveIfAbsent);
    }
}

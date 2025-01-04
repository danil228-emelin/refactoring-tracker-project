package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.RoleDAO;
import ru.ifmo.insys1.dao.UserDAO;
import ru.ifmo.insys1.entity.Role;
import ru.ifmo.insys1.entity.User;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.RegistrationRequest;
import ru.ifmo.insys1.response.RegistrationResponse;
import ru.ifmo.insys1.security.PasswordHash;
import ru.ifmo.insys1.service.RegistrationService;

import java.util.Objects;

import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static ru.ifmo.insys1.constants.RoleConstant.CLIENT;

@Slf4j
@ApplicationScoped
public class RegistrationServiceImpl implements RegistrationService {

    private static final String USERS_USERNAME_KEY = "users_username_key";

    @Inject
    private UserDAO userDAO;

    @Inject
    private RoleDAO roleDAO;

    @Inject
    private ModelMapper mapper;

    @Inject
    private PasswordHash passwordHash;

    @Override
    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {
        User converted = mapper.map(request, User.class);
        Role userRole = roleDAO.getRoleByName(CLIENT);
        converted.setRole(userRole);
        converted.setPassword(passwordHash.hash(converted.getPassword()));
        persistUser(converted);
        return mapper.map(converted, RegistrationResponse.class);
    }

    private void persistUser(User converted) {
        try {
            userDAO.save(converted);
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException v) {
                if (Objects.equals(v.getConstraintName(), USERS_USERNAME_KEY)) {
                    throw new ServiceException(CONFLICT, "Username is already in use");
                }
            }
            throw e;
        }
    }
}

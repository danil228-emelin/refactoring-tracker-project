package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static ru.ifmo.insys1.constants.RoleConstant.USER;

@ApplicationScoped
public class RegistrationServiceImpl implements RegistrationService {

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
        String username = request.getUsername();

        if (userDAO.isUsernameExist(username)) {
            throw new ServiceException(CONFLICT, "Username is already in use");
        }

        User converted = mapper.map(request, User.class);
        Role userRole = roleDAO.getRoleByName(USER);

        converted.setRole(userRole);
        converted.setPassword(passwordHash.hash(converted.getPassword()));

        userDAO.save(converted);

        return mapper.map(converted, RegistrationResponse.class);
    }

}

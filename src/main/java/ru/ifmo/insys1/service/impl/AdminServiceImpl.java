package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.ApplicationDAO;
import ru.ifmo.insys1.dao.RoleDAO;
import ru.ifmo.insys1.dao.UserDAO;
import ru.ifmo.insys1.entity.Application;
import ru.ifmo.insys1.entity.Role;
import ru.ifmo.insys1.entity.User;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.response.ApplicationDTO;
import ru.ifmo.insys1.service.AdminService;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static ru.ifmo.insys1.constants.RoleConstant.ADMIN;

@ApplicationScoped
public class AdminServiceImpl implements AdminService {

    @Inject
    private ApplicationDAO applicationDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private RoleDAO roleDAO;

    @Inject
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void submitApplication() {
        applicationDAO.save(new Application());
    }

    @Override
    @Transactional
    public void acceptApplication(ApplicationDTO applicationDTO) {
        Optional<Application> application = applicationDAO.get(applicationDTO.getId());

        if (application.isEmpty()) {
            throw new ServiceException(
                    NOT_FOUND,
                    "Application with id " + applicationDTO.getId() + " not found"
            );
        }

        Role adminRole = roleDAO.getRoleByName(ADMIN);

        Optional<User> optionalCaller = userDAO.findById(
                application.get()
                        .getCreatedBy()
        );

        if (optionalCaller.isEmpty()) {
            throw new ServiceException(
                    NOT_FOUND,
                    "User with id " + applicationDTO.getId() + " not found"
            );
        }

        User caller = optionalCaller.get();
        caller.setRole(adminRole);

        userDAO.update(caller);
        applicationDAO.delete(application.get());
    }

    @Override
    public List<ApplicationDTO> getAllApplications(int page, int size) {
        return applicationDAO.getAllApplications(page, size)
                .stream()
                .map(application -> modelMapper.map(application, ApplicationDTO.class))
                .toList();
    }
}

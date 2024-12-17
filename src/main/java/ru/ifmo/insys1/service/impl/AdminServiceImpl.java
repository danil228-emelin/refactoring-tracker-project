package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.ApplicationDAO;
import ru.ifmo.insys1.dao.RoleDAO;
import ru.ifmo.insys1.dao.UserDAO;
import ru.ifmo.insys1.entity.Application;
import ru.ifmo.insys1.entity.User;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.response.ApplicationDTO;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.AdminService;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class AdminServiceImpl implements AdminService {

    @Inject
    private ApplicationDAO applicationDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private SecurityManager securityManager;

    @Inject
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void submitApplication() {
        if (!userDAO.findAdmins().isEmpty()) {
            applicationDAO.save(new Application());
            return;
        }

        Long callerPrincipal = securityManager.getCallerPrincipal();

        setAdminRole(callerPrincipal);
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

        Long applicationOwner = application.get()
                .getCreatedBy();

        setAdminRole(applicationOwner);

        applicationDAO.delete(application.get());
    }

    @Override
    public List<ApplicationDTO> getAllApplications(int page, int size) {
        return applicationDAO.getAllApplications(page, size)
                .stream()
                .map(this::convertToApplicationDTO)
                .toList();
    }

    private ApplicationDTO convertToApplicationDTO(Application application) {
        User user = userDAO.findById(application.getCreatedBy()).get();
        ApplicationDTO map = modelMapper.map(application, ApplicationDTO.class);
        map.setUsername(user.getUsername());

        return map;
    }

    @Transactional
    private void setAdminRole(Long userId) {
        userDAO.setAdminRole(userId);
    }
}

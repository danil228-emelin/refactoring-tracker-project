package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.CoordinatesDAO;
import ru.ifmo.insys1.request.CoordinatesRequest;
import ru.ifmo.insys1.entity.Coordinates;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.response.CoordinatesResponse;
import ru.ifmo.insys1.security.SecurityManager;
import ru.ifmo.insys1.service.CoordinatesService;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class CoordinatesServiceImpl implements CoordinatesService {

    @Inject
    private CoordinatesDAO coordinatesDAO;

    @Inject
    private SecurityManager securityManager;

    @Inject
    private ModelMapper modelMapper;

    @Override
    public CoordinatesResponse getCoordinates(Long id) {
        Optional<Coordinates> optionalCoordinates = coordinatesDAO.findById(id);

        if (optionalCoordinates.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Movie not found");
        }

        return modelMapper.map(optionalCoordinates.get(), CoordinatesResponse.class);
    }

    @Override
    public List<CoordinatesResponse> getAllCoordinates(int page, int size) {
        List<Coordinates> allCoordinates = coordinatesDAO.findAll(page, size);

        return allCoordinates.stream()
                .map(c -> modelMapper.map(c, CoordinatesResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public CoordinatesResponse createCoordinates(CoordinatesRequest coordinates) {
        Coordinates converted = modelMapper.map(coordinates, Coordinates.class);

        coordinatesDAO.save(converted);

        return modelMapper.map(converted, CoordinatesResponse.class);
    }

    @Override
    @Transactional
    public void updateCoordinates(Long id, CoordinatesRequest coordinatesRequest) {
        Optional<Coordinates> optionalCoordinates = coordinatesDAO.findById(id);

        if (optionalCoordinates.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Movie not found");
        }

        Coordinates coordinates = optionalCoordinates.get();

        securityManager.throwIfUserHasNotAccessToResource(coordinates.getCreatedBy());

        modelMapper.map(coordinatesRequest, coordinates);

        coordinatesDAO.update(coordinates);
    }

    @Override
    @Transactional
    public void deleteCoordinates(Long id) {
        Optional<Coordinates> optionalCoordinates = coordinatesDAO.findById(id);

        if (optionalCoordinates.isEmpty()) {
            throw new ServiceException(NOT_FOUND, "Movie not found");
        }

        Coordinates coordinates = optionalCoordinates.get();

        securityManager.throwIfUserHasNotAccessToResource(coordinates.getCreatedBy());

        coordinatesDAO.delete(id);
    }
}

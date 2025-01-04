package ru.ifmo.insys1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.dao.LocationDAO;
import ru.ifmo.insys1.entity.Location;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.LocationDTO;
import ru.ifmo.insys1.response.LocationResponse;
import ru.ifmo.insys1.service.LocationService;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LocationServiceImpl implements LocationService {

    @Inject
    private LocationDAO locationDAO;

    @Inject
    private ModelMapper mapper;

    @Override
    public Optional<LocationResponse> findById(Integer id) {
        return locationDAO.findById(id)
                .map(location -> mapper.map(location, LocationResponse.class));
    }

    @Override
    public List<LocationResponse> findAll() {
        return locationDAO.findAll()
                .stream()
                .map(location -> mapper.map(location, LocationResponse.class))
                .toList();
    }

    @Override
    @Transactional
    public void save(LocationDTO location) {
        var entity = mapper.map(location, Location.class);
        locationDAO.save(entity);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        locationDAO.delete(id);
    }
}

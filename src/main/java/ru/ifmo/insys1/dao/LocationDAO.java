package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import ru.ifmo.insys1.entity.Location;
import ru.ifmo.insys1.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class LocationDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Location> findById(Integer id) {
        return Optional.ofNullable(em.find(Location.class, id));
    }

    public List<Location> findAll() {
        return em.createQuery("FROM Location", Location.class)
                .getResultList();
    }

    public void save(Location location) {
        em.persist(location);
    }

    public void delete(Integer id) {
        Location locationById = em.find(Location.class, id);

        if (locationById == null) {
            throw new ServiceException(NOT_FOUND, "Location not found");
        }

        Long countBoundEntities = getCountBoundEntities(locationById);

        if (countBoundEntities > 0) {
            throw new ServiceException(CONFLICT, "Location has bound entities");
        }

        em.remove(locationById);
    }

    private Long getCountBoundEntities(Location location) {
        String query = "SELECT COUNT(id) FROM CargoStatus WHERE location = :location";
        return em.createQuery(query, Long.class)
                .setParameter("location", location)
                .getSingleResult();
    }
}

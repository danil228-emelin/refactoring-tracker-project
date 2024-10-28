package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import ru.ifmo.insys1.entity.Location;
import ru.ifmo.insys1.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class LocationDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Location> findById(Long id) {
        return Optional.ofNullable(em.find(Location.class, id));
    }

    public List<Location> findAll(int page, int size) {
        return em.createQuery("FROM Location", Location.class)
                .setFirstResult((page - 1))
                .setMaxResults(size)
                .getResultList();
    }

    public void save(Location location) {
        em.persist(location);
    }

    public void update(@Valid Location location) {
        em.merge(location);
    }

    public void delete(Long id) {
        Location locationById = em.find(Location.class, id);

        if (locationById == null) {
            throw new ServiceException(NOT_FOUND, "Location not found");
        }

        em.remove(locationById);
    }
}

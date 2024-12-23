package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.ifmo.insys1.entity.Coordinates;
import ru.ifmo.insys1.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
@Slf4j
public class CoordinatesDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Coordinates> findById(Long id) {
        return Optional.ofNullable(em.find(Coordinates.class, id));
    }

    public List<Coordinates> findAll(int page, int size) {
        return em.createQuery("FROM Coordinates", Coordinates.class)
                .setFirstResult((page - 1))
                .setMaxResults(size)
                .getResultList();
    }

    public void save(Coordinates converted) {
        em.persist(converted);
    }

    public void update(@Valid Coordinates coordinates) {
        log.info("Updated coordinates: {}", coordinates);
        em.merge(coordinates);
    }

    public void delete(Long id) {
        Coordinates coordinatesById = em.find(Coordinates.class, id);

        if (coordinatesById == null) {
            throw new ServiceException(NOT_FOUND, "Coordinates not found");
        }

        Long countBoundEntities = getCountBoundEntities(coordinatesById);

        if (countBoundEntities > 0) {
            throw new ServiceException(CONFLICT, "Coordinates has bound entities");
        }

        em.remove(coordinatesById);
    }

    private Long getCountBoundEntities(Coordinates coordinates) {
        String query = "SELECT COUNT(id) FROM Movie WHERE coordinates = :coordinates";
        return em.createQuery(query, Long.class)
                .setParameter("coordinates", coordinates)
                .getSingleResult();
    }
}

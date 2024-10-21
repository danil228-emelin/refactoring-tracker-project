package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ru.ifmo.insys1.entity.Coordinates;
import ru.ifmo.insys1.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
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

    @Transactional
    public void save(Coordinates converted) {
        em.persist(converted);
    }

    @Transactional(REQUIRES_NEW)
    public void update(Coordinates coordinates) {
        em.merge(coordinates);
    }

    @Transactional
    public void delete(Long id) {
        Coordinates coordinatesById = em.find(Coordinates.class, id);

        if (coordinatesById == null) {
            throw new ServiceException(NOT_FOUND, "Coordinates not found");
        }

        em.remove(coordinatesById);
    }
}

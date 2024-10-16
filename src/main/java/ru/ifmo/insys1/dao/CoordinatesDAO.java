package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.ifmo.insys1.entity.Coordinates;

import java.util.List;
import java.util.Optional;

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

    public void save(Coordinates converted) {
    }

    public void update(Coordinates coordinates) {
    }

    public void delete(Long id) {
    }
}

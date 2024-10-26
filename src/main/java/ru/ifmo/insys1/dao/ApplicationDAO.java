package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.ifmo.insys1.entity.Application;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ApplicationDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(Application application) {
        em.persist(application);
    }

    public Optional<Application> get(Long id) {
        return Optional.ofNullable(em.find(Application.class, id));
    }

    public List<Application> getAllApplications(int page, int size) {
        return em.createQuery("FROM Application", Application.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    public void delete(Application application) {
        em.remove(application);
    }
}

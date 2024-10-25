package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import ru.ifmo.insys1.entity.Person;
import ru.ifmo.insys1.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class PersonDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(em.find(Person.class, id));
    }

    public List<Person> findAll(int page, int size) {
        return em.createQuery("FROM Person", Person.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
    public void save(Person person) {
        em.persist(person);
    }

    @Transactional
    public void update(@Valid Person person) {
        em.merge(person);
    }

    @Transactional
    public void delete(Long id) {
        Person personById = em.find(Person.class, id);

        if (personById == null) {
            throw new ServiceException(NOT_FOUND, "Person not found");
        }

        em.remove(personById);
    }
}

package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.ifmo.insys1.entity.Label;

import java.util.Optional;

@ApplicationScoped
public class LabelsDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Label> findById(Integer id) {
        return Optional.ofNullable(em.find(Label.class, id));
    }

    public Label save(Label label) {
        em.persist(label);
        return label;
    }
}

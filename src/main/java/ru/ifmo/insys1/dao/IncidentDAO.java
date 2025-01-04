package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.ifmo.insys1.entity.Incident;

import java.util.List;

@ApplicationScoped
public class IncidentDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Incident> getByClientId(Integer clientId) {
        return em.createQuery("FROM Incident i WHERE i.cargo.order.client.id = :client", Incident.class)
                .setParameter("client", clientId)
                .getResultList();
    }

    public void save(Incident incident) {
        em.persist(incident);
    }
}

package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.entity.CargoRequest;
import ru.ifmo.insys1.exception.ServiceException;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CargoRequestDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(CargoRequest cargoRequest) {
        em.persist(cargoRequest);
    }

    public List<CargoRequest> findByClientName(String clientName) {
        return em.createQuery("FROM CargoRequest cr " +
                        "JOIN User cl ON cl.id = cr.ownerOid " +
                        "WHERE cl.username = :client", CargoRequest.class)
                .setParameter("client", clientName)
                .getResultList();
    }

    public List<CargoRequest> findByOwner(Integer ownerId) {
        return em.createQuery("FROM CargoRequest c WHERE c.ownerOid = :client", CargoRequest.class)
                .setParameter("client", ownerId)
                .getResultList();
    }

    public Optional<CargoRequest> findById(Integer id) {
        return Optional.ofNullable(
                em.createQuery("FROM CargoRequest c WHERE c.id = :id", CargoRequest.class)
                        .setParameter("id", id)
                        .getSingleResultOrNull()
        );
    }

    public void delete(Integer id) {
        findById(id).ifPresentOrElse(
                req -> em.remove(req),
                () -> {
                    throw new ServiceException(Response.Status.NOT_FOUND, "Cargo request not found");
                }
        );
    }
}

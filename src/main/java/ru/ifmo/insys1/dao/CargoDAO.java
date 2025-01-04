package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.ifmo.insys1.entity.Cargo;
import ru.ifmo.insys1.request.CreateCargoDTO;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CargoDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Cargo> getBySscc(String sscc) {
        List<Cargo> cargoResponse = em.createQuery(
                        """
                                FROM Cargo c
                                WHERE c.label.ssccCode = :sscc""",
                        Cargo.class
                )
                .setParameter("sscc", sscc)
                .getResultList();
        return cargoResponse.stream()
                .findFirst();
    }

    public void save(CreateCargoDTO request) {
        String callProcedure = "CALL register_cargo(:cargo_request_id, :weight, :order_id, :label_oid)";
        em.createNativeQuery(callProcedure)
                .setParameter("cargo_request_id", request.getCargoRequestId())
                .setParameter("weight", request.getWeight())
                .setParameter("order_id", request.getOrderId())
                .setParameter("label_oid", request.getLabelId())
                .executeUpdate();
    }
}

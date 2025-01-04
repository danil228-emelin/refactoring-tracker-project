package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.ifmo.insys1.entity.Notification;

import java.util.List;

@ApplicationScoped
public class NotificationDAO {

    @PersistenceContext
    private EntityManager em;

    public void deleteAllByOwnerId(Integer ownerId) {
        em.createQuery("delete from Notification n where n.ownerOid = :ownerId")
                .setParameter("ownerId", ownerId)
                .executeUpdate();
    }

    public List<Notification> getAllByOwner(Integer ownerId) {
        return em.createQuery("from Notification n where n.ownerOid = :ownerId", Notification.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
    }

    public void save(Notification notification) {
        em.createNativeQuery("INSERT INTO notifications(description, is_positive, owner_oid) VALUES (:description, :isPositive, :ownerId)")
                .setParameter("description", notification.getDescription())
                .setParameter("isPositive", notification.getIsPositive())
                .setParameter("ownerId", notification.getOwnerOid())
                .executeUpdate();
    }
}

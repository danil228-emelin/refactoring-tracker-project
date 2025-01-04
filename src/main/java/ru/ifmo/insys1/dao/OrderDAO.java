package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.entity.Order;
import ru.ifmo.insys1.exception.ServiceException;
import ru.ifmo.insys1.request.OrderDeliveryDateRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class OrderDAO {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserDAO userDAO;

    public Order persist(String clientName) {
        var user = userDAO.findByUsername(clientName);
        if (user.isEmpty()) {
            throw new ServiceException(Response.Status.NOT_FOUND, "Client not found");
        }
        var entity = new Order();
        entity.setCreationDate(LocalDateTime.now());
        entity.setClient(user.get());
        em.persist(entity);
        return entity;
    }

    public Optional<Order> get(Integer id) {
        return em.createQuery("FROM Order o WHERE o.id = :id", Order.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Order changeDeliveryDate(Integer id, OrderDeliveryDateRequest deliveryDateRequest) {
        var order = get(id);
        if (order.isEmpty()) {
            throw new ServiceException(Response.Status.NOT_FOUND, "Order not found");
        }
        order.get().setDeliveryDate(LocalDateTime.parse(deliveryDateRequest.getDeliveryDate()));
        return order.get();
    }
}

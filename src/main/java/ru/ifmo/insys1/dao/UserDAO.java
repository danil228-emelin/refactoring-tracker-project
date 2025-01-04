package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.ifmo.insys1.entity.User;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<User> findByUsername(String username) {
        return getUserByUsername(username).stream()
                .findFirst();
    }

    public Optional<User> findById(Integer id) {
        return em.createQuery("FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<User> findManagers() {
        return em.createQuery("FROM User u WHERE u.role.roleName = 'MANAGER'", User.class)
                .getResultList();
    }

    public void save(User user) {
        em.persist(user);
    }

    private List<User> getUserByUsername(String username) {
        return em.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
    }
}

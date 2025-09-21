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

    public Optional<User> findById(Long id) {
        return em.createQuery("FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public void setAdminRole(Long userId) {
        em.createNativeQuery("UPDATE users SET role_id = (SELECT id FROM role WHERE role_name = 'ADMIN') WHERE id = :id")
                .setParameter("id", userId)
                .executeUpdate();
    }

    public List<User> findAdmins() {
        return em.createQuery("FROM User u WHERE u.role.roleName = 'ADMIN'", User.class)
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

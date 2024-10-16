package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    public boolean isUsernameExist(String username) {
        return !getUserByUsername(username).isEmpty();
    }

    private List<User> getUserByUsername(String username) {
        return em.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
    }
}

package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ru.ifmo.insys1.entity.Role;

@ApplicationScoped
@Transactional
public class RoleDAO {

    @PersistenceContext
    private EntityManager em;

    public boolean contains(String name) {
        return !em.createQuery("FROM Role r WHERE r.roleName = :name", Role.class)
                .setParameter("name", name)
                .getResultList()
                .isEmpty();
    }

    public void saveIfAbsent(String name) {
        if (!contains(name)) {
            em.persist(new Role(name));
        }
    }

    public Role getRoleByName(String name) {
        var resultList = em.createQuery("FROM Role r WHERE r.roleName = :name", Role.class)
                .setParameter("name", name)
                .getResultList();

        return resultList.isEmpty() ? null : resultList.iterator().next();
    }
}

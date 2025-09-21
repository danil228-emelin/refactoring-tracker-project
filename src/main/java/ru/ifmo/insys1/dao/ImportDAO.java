package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import ru.ifmo.insys1.entity.Import;
import ru.ifmo.insys1.request.ImportRequest;

import java.util.List;

@ApplicationScoped
public class ImportDAO {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ModelMapper mapper;

    public void persist(ImportRequest importRequest) {
        Import entity = mapper.map(importRequest, Import.class);
        em.persist(entity);
    }

    public List<Import> getAll() {
        return em.createQuery("FROM Import", Import.class)
                .getResultList();
    }
}

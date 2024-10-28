package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import ru.ifmo.insys1.entity.Movie;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.entity.Person;
import ru.ifmo.insys1.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class MovieDAO {

    @PersistenceContext
    private EntityManager em;

    public Optional<Movie> findById(Long id) {
        return Optional.ofNullable(em.find(Movie.class, id));
    }

    public List<Movie> findAll(int page, int size, String filter, String filterColumn, String sorted) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movie = cq.from(Movie.class);

        boolean isFilterValid = filter != null &&
                filterColumn != null &&
                !filter.isEmpty() &&
                !filterColumn.isEmpty();

        if (isFilterValid) {
            cq.where(cb.like(movie.get("title"), "%" + filter + "%"));
        }

        if (sorted != null && !sorted.isEmpty()) {
            cq.orderBy(cb.asc(movie.get(sorted)));
        }

        TypedQuery<Movie> query = em.createQuery(cq);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    @Transactional
    public void save(Movie movie) {
        em.persist(movie);
    }

    @Transactional
    public void update(@Valid Movie movie) {
        em.merge(movie);
    }

    @Transactional
    public void delete(Long id) {
        Movie movieById = em.find(Movie.class, id);

        if (movieById == null) {
            throw new ServiceException(NOT_FOUND, "Movie not found");
        }

        em.remove(movieById);
    }

    @Transactional
    public void deleteMovieByTagline(String tagline) {
        em.createNativeQuery("""
                            DELETE FROM movie AS m\s
                            WHERE m.id = (
                                 SELECT mov.id\s
                                 FROM movie AS mov\s
                                 WHERE mov.tag_line = :tagline\s
                                 LIMIT 1
                             )
                        \s""")
                .setParameter("tagline", tagline)
                .executeUpdate();
    }

    public Long getCountMoviesWithTagline(String tagline) {
        return em.createQuery("SELECT COUNT(m) FROM Movie AS m WHERE m.tagline = :tagline", Long.class)
                .setParameter("tagline", tagline)
                .getSingleResult();
    }

    public Long getCountMoviesWithGenre(MovieGenre movieGenre) {
        return em.createQuery("SELECT COUNT(m) FROM Movie AS m WHERE m.genre = :movieGenre", Long.class)
                .setParameter("movieGenre", movieGenre)
                .getSingleResult();
    }

    public List<Person> getOperatorsWithoutOscar() {
        return em.createQuery("SELECT m.operator FROM Movie AS m WHERE m.oscarsCount = 0", Person.class)
                .getResultList();
    }

    public void incrementOscarsCountForAllMoviesWithRCategory() {
        em.createQuery("UPDATE Movie SET oscarsCount = oscarsCount + 1 WHERE mpaaRating = 'R'")
                .executeUpdate();
    }
}

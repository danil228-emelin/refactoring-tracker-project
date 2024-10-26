package ru.ifmo.insys1.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    public List<Movie> findAll(int page, int size) {
        return em.createQuery("FROM Movie", Movie.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
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
                                 WHERE mov.tagline = :tagline\s
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
        return em.createQuery("SELECT COUNT(m) FROM Movie AS m WHERE m.genre = :genre", Long.class)
                .setParameter("genre", movieGenre)
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

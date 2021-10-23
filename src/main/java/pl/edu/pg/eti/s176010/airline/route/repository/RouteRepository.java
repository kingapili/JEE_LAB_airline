package pl.edu.pg.eti.s176010.airline.route.repository;

import lombok.extern.java.Log;
import pl.edu.pg.eti.s176010.airline.repository.Repository;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Repository for route entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
@Log
public class RouteRepository implements Repository<Route, Long> {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<Route> find(Long id) {
        return Optional.ofNullable(em.find(Route.class, id));
    }

    @Override
    public List<Route> findAll() {
        return em.createQuery("select r from Route r", Route.class).getResultList();
    }

    @Override
    public void create(Route entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Route entity) {
        em.remove(em.find(Route.class, entity.getId()));
    }

    @Override
    public void update(Route entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Route entity) {
        em.detach(entity);
    }

}

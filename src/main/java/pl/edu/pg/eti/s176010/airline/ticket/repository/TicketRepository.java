package pl.edu.pg.eti.s176010.airline.ticket.repository;

import lombok.extern.java.Log;
import pl.edu.pg.eti.s176010.airline.repository.Repository;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Repository for ticket entity. Repositories should be used in business layer (e.g.: in services).
 */

@Dependent
@Log
public class TicketRepository implements Repository<Ticket, Long> {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Ticket> find(Long id) {
        return Optional.ofNullable(em.find(Ticket.class, id));
    }

    @Override
    public List<Ticket> findAll() {
        return em.createQuery("select t from Ticket t", Ticket.class).getResultList();
    }

    @Override
    public void create(Ticket entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Ticket entity) {
        em.remove(em.find(Ticket.class, entity.getId()));
    }

    @Override
    public void update(Ticket entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Ticket entity) {
        em.detach(entity);
    }


    /**
     * Seeks for all tickets for route.
     *
     * @param route tickets' route
     * @return list (can be empty) of user's tickets
     */
    public List<Ticket> findAllByRoute(Route route) {
        return em.createQuery("select t from Ticket t where t.route = :route", Ticket.class)
                .setParameter("route", route)
                .getResultList();
    }

    /**
     * Seeks for single user's ticket.
     *
     * @param id   ticket's id
     * @param route ticket's route
     * @return container (can be empty) with ticket
     */
    public Optional<Ticket> findByRouteAndId(Route route, Long id) {
        try {
            return Optional.of(em.createQuery("select t from Ticket t where t.id = :id and t.route = :route", Ticket.class)
                    .setParameter("route", route)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Seeks for all user's tickets.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's Tickets.
     */
    public List<Ticket> findAllByUser(User user) {
        return em.createQuery("select t from Ticket t where t.user = :user", Ticket.class)
                .setParameter("user", user)
                .getResultList();
    }

    /**
     * Seeks for all user's tickets.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's Tickets.
     */
    public List<Ticket> findAllByUserAndRoute(User user, Route route) {
        return em.createQuery("select t from Ticket t where t.user = :user and t.route = :route", Ticket.class)
                .setParameter("user", user)
                .setParameter("route", route)
                .getResultList();
    }

    /**
     * Seeks for single user's ticket.
     *
     * @param id   ticket's id
     * @param user ticket's owner
     * @return container (can be empty) with ticket
     */
    public Optional<Ticket> findByIdAndUser(Long id, User user) {
        try {
            return Optional.of(em.createQuery("select t from Ticket t where t.id = :id and t.user = :user", Ticket.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Seeks for single user's ticket.
     *
     * @param id   ticket's id
     * @param user ticket's owner
     * @return container (can be empty) with ticket
     */
    public Optional<Ticket> findByIdAndUserAndRoute(Long id, User user, Route route) {
        try {
            return Optional.of(em.createQuery(
                    "select t from Ticket t where t.id = :id and t.user = :user and t.route = :route", Ticket.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .setParameter("route", route)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}

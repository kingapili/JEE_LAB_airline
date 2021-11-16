package pl.edu.pg.eti.s176010.airline.user.repository;

import lombok.extern.java.Log;
import pl.edu.pg.eti.s176010.airline.repository.Repository;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Repository for User entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
@Log
public class UserRepository implements Repository<User, Long> {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(em.find(User.class, entity.getId()));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public void detach(User entity) {
        em.detach(entity);
    }

    /**
     * Seeks for single user using email and password. Can be use in authentication module.
     *
     * @param email    user's email
     * @param password user's password (hash)
     * @return container (can be empty) with user
     */
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try {
            return Optional.of(em.createQuery("select u from User u where u.email = :email and u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.of(em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}

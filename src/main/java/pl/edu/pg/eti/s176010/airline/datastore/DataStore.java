package pl.edu.pg.eti.s176010.airline.datastore;

import lombok.extern.java.Log;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.serialization.CloningUtility;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * For the sake of simplification instead of using real database this example is using an data source object which
 * should be put in servlet context in a single instance. In order to avoid {@link
 * java.util.ConcurrentModificationException} all methods are synchronized. Normally synchronization would be carried on
 * by the database server.
 */
@Log
@ApplicationScoped
public class DataStore {

    /**
     * Set of all available routes.
     */
    private Set<Route> routes = new HashSet<>();

    /**
     * Set of all tickets.
     */
    private Set<Ticket> tickets = new HashSet<>();

    /**
     * Set of all users.
     */
    private Set<User> users = new HashSet<>();


    /**
     * Seeks for single user.
     *
     * @param id user's id
     * @return container (can be empty) with user
     */
    public synchronized Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Seeks for all users.
     *
     * @return collection of all users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new user.
     *
     * @param user new user to be stored
     * @throws IllegalArgumentException if user with provided login already exists
     */
    public synchronized void createUser(User user) throws IllegalArgumentException {
        findUser(user.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user email \"%s\" is not unique", user.getEmail()));
                },
                () -> users.add(CloningUtility.clone(user)));
    }

    /**
     * Updates existing user.
     *
     * @param user user to be updated
     * @throws IllegalArgumentException if user with the same id does not exist
     */
    public synchronized void updateUser(User user) throws IllegalArgumentException {
        findUser(user.getId()).ifPresentOrElse(
                original -> {
                    users.remove(original);
                    users.add(CloningUtility.clone(user));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The user with id \"%d\" does not exist", user.getId()));
                });
    }
}

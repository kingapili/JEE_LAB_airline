package pl.edu.pg.eti.s176010.airline.datastore;

import lombok.extern.java.Log;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.serialization.CloningUtility;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    /**
     * Seeks for all routes.
     *
     * @return list (can be empty) of all routes
     */
    public synchronized List<Route> findAllRoutes() {
        return new ArrayList<>(routes);
    }

    /**
     * Seeks for the route in the memory storage.
     *
     * @param id id of the route
     * @return container (can be empty) with route if present
     */
    public Optional<Route> findRoute(Long id) {
        return routes.stream()
                .filter(route -> route.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Stores new route.
     *
     * @param route new route to be stored
     * @throws IllegalArgumentException if route with provided name already exists
     */
    public synchronized void createRoute(Route route) throws IllegalArgumentException {
        route.setId(findAllRoutes().stream().mapToLong(Route::getId).max().orElse(0) + 1);
        routes.add(route);
    }

    /**
     * Updates existing route.
     *
     * @param route route to be updated
     * @throws IllegalArgumentException if route with the same id does not exist
     */
    public synchronized void updateRoute(Route route) throws IllegalArgumentException {
        findRoute(route.getId()).ifPresentOrElse(
                original -> {
                    routes.remove(original);
                    routes.add(route);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The route with id \"%d\" does not exist", route.getId()));
                });
    }

    /**
     * Deletes existing ticket.
     *
     * @param id ticket's id
     * @throws IllegalArgumentException if ticket with provided id does not exist
     */
    public synchronized void deleteRoute(Long id) throws IllegalArgumentException {
        findRoute(id).ifPresentOrElse(
                original -> routes.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The route with id \"%d\" does not exist", id));
                });
    }

    /**
     * Seeks for all tickets.
     *
     * @return list (can be empty) of all tickets
     */
    public synchronized List<Ticket> findAllTickets() {
        return tickets.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    /**
     * Seeks for single ticket.
     *
     * @param id ticket's id
     * @return container (can be empty) with ticket
     */
    public synchronized Optional<Ticket> findTicket(Long id) {
        return tickets.stream()
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Stores new ticket.
     *
     * @param ticket new ticket
     */
    public synchronized void createTicket(Ticket ticket) throws IllegalArgumentException {
        ticket.setId(findAllTickets().stream().mapToLong(Ticket::getId).max().orElse(0) + 1);
        tickets.add(ticket);
    }

    /**
     * Updates existing ticket.
     *
     * @param ticket ticket to be updated
     * @throws IllegalArgumentException if ticket with the same id does not exist
     */
    public synchronized void updateTicket(Ticket ticket) throws IllegalArgumentException {
        findTicket(ticket.getId()).ifPresentOrElse(
                original -> {
                    tickets.remove(original);
                    tickets.add(ticket);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The ticket with id \"%d\" does not exist", ticket.getId()));
                });
    }

    /**
     * Deletes existing ticket.
     *
     * @param id ticket's id
     * @throws IllegalArgumentException if ticket with provided id does not exist
     */
    public synchronized void deleteTicket(Long id) throws IllegalArgumentException {
        findTicket(id).ifPresentOrElse(
                original -> tickets.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The ticket with id \"%d\" does not exist", id));
                });
    }

    /**
     * Get stream to be used (for filtering, sorting, etc) in repositories.
     *
     * @return ticket's stream
     */
    public Stream<Ticket> getTicketStream() {
        return tickets.stream();
    }

}

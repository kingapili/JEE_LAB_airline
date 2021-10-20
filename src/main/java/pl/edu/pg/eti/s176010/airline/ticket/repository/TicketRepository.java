package pl.edu.pg.eti.s176010.airline.ticket.repository;



import pl.edu.pg.eti.s176010.airline.datastore.DataStore;
import pl.edu.pg.eti.s176010.airline.repository.Repository;
import pl.edu.pg.eti.s176010.airline.serialization.CloningUtility;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository for ticket entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
public class TicketRepository implements Repository<Ticket, Long> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public TicketRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Ticket> find(Long id) {
        return store.findTicket(id);
    }

    @Override
    public List<Ticket> findAll() {
        return store.findAllTickets();
    }

    @Override
    public void create(Ticket entity) {
        store.createTicket(entity);
    }

    @Override
    public void delete(Ticket entity) {
        store.deleteTicket(entity.getId());
    }

    @Override
    public void update(Ticket entity) {
        store.updateTicket(entity);
    }

    /**
     * Seeks for single user's ticket.
     *
     * @param id   ticket's id
     * @param route ticket's route
     * @return container (can be empty) with ticket
     */
    public Optional<Ticket> findByIdAndRoute(Long id, Route route) {
        return store.getTicketStream()
                .filter(ticket -> ticket.getRoute().equals(route))
                .filter(ticket -> ticket.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * Seeks for all tickets for route.
     *
     * @param route tickets' route
     * @return list (can be empty) of user's tickets
     */
    public List<Ticket> findAllByRoute(Route route) {
        return store.getTicketStream()
                .filter(ticket -> ticket.getRoute().equals(route))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

}

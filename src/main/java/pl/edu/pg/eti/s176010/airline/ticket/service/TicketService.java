package pl.edu.pg.eti.s176010.airline.ticket.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.route.repository.RouteRepository;
import pl.edu.pg.eti.s176010.airline.ticket.repository.TicketRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding ticket entity.
 */
@ApplicationScoped
@NoArgsConstructor//Empty constructor is required for creating proxy while CDI injection.
public class TicketService {

    /**
     * Repository for ticket entity.
     */
    private TicketRepository ticketRepository;

    /**
     * Repository for route entity.
     */
    private RouteRepository routeRepository;


    /**
     * @param ticketRepository repository for ticket entity
     * @param routeRepository      repository for route entity
     */
    @Inject
    public TicketService(TicketRepository ticketRepository, RouteRepository routeRepository) {
        this.ticketRepository = ticketRepository;
        this.routeRepository = routeRepository;
    }

    /**
     * Finds single ticket.
     *
     * @param id ticket's id
     * @return container with ticket
     */
    public Optional<Ticket> find(Long id) {
        return ticketRepository.find(id);
    }


    /**
     * @return all available tickets
     */
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    /**
     * @return all available tickets of the route
     */
    public Optional<List<Ticket>> findAllByRoute(Long routeId) {
        Optional<Route> route = routeRepository.find(routeId);
        if (route.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ticketRepository.findAllByRoute(route.get()));
        }
    }

    /**
     * Creates new ticket.
     *
     * @param ticket new ticket
     */
    public void create(Ticket ticket) {
        ticketRepository.create(ticket);
    }

    /**
     * Assigns currently logged route to passed new ticket and saves it in data store,
     *
     * @param ticket new ticket to be saved
     */
    public void createForRoute(Ticket ticket, Long routeId) {
        ticket.setRoute(routeRepository.find(routeId).orElseThrow());
        ticketRepository.create(ticket);
    }

    /**
     * Updates existing ticket.
     *
     * @param ticket ticket to be updated
     */
    public void update(Ticket ticket) {
        ticketRepository.update(ticket);
    }

    /**
     * Deletes existing ticket.
     *
     * @param ticketId existing ticket's id to be deleted
     */
    public void delete(Long ticketId) {
        ticketRepository.delete(ticketRepository.find(ticketId).orElseThrow());
    }

    /**
     * deletes all available tickets of the route
     */
    public void deleteAllByRoute(Long routeId) {
        List<Ticket> tickets = ticketRepository.findAllByRoute(routeRepository.find(routeId).orElseThrow());
        for(Ticket ticket : tickets){ ticketRepository.delete(ticket); }
    }

    public Optional<Ticket> findByRoute(Long routeId, Long id) {
        Optional<Route> route = routeRepository.find(routeId);
        if (route.isEmpty()) {
            return Optional.empty();
        } else {
            return ticketRepository.findByRouteAndId(route.get(), id);
        }
    }
}

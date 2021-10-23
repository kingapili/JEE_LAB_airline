package pl.edu.pg.eti.s176010.airline.ticket.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.route.repository.RouteRepository;
import pl.edu.pg.eti.s176010.airline.ticket.repository.TicketRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
    @Transactional
    public void create(Ticket ticket) {
        ticketRepository.create(ticket);
        routeRepository.find(ticket.getRoute().getId()).ifPresent(route -> route.getTickets().add(ticket));
    }

    /**
     * Updates existing ticket.
     *
     * @param ticket ticket to be updated
     */
    @Transactional
    public void update(Ticket ticket) {
        Ticket original = ticketRepository.find(ticket.getId()).orElseThrow();
        ticketRepository.detach(original);
        if (!original.getRoute().getId().equals(ticket.getRoute().getId())) {
            original.getRoute().getTickets().removeIf(ticketToRemove -> ticketToRemove.getId().equals(ticket.getId()));
            routeRepository.find(ticket.getRoute().getId()).ifPresent(route -> route.getTickets().add(ticket));
        }
        ticketRepository.update(ticket);
    }

    /**
     * Deletes existing ticket.
     *
     * @param ticketId existing ticket's id to be deleted
     */
    @Transactional
    public void delete(Long ticketId) {
        Ticket ticket = ticketRepository.find(ticketId).orElseThrow();
        ticket.getRoute().getTickets().remove(ticket);
        ticketRepository.delete(ticket);
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

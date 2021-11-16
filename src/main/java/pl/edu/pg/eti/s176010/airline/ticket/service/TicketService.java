package pl.edu.pg.eti.s176010.airline.ticket.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.route.repository.RouteRepository;
import pl.edu.pg.eti.s176010.airline.ticket.repository.TicketRepository;
import pl.edu.pg.eti.s176010.airline.ticket.service.interceptor.binding.LogDeleteTicket;
import pl.edu.pg.eti.s176010.airline.ticket.service.interceptor.binding.LogEditTicket;
import pl.edu.pg.eti.s176010.airline.ticket.service.interceptor.binding.LogNewTicket;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.repository.UserRepository;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding ticket entity.
 */
@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed({"ADMIN","USER"})//{Role.ADMIN,Role.USER}) TODO
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
     * Repository for user entity.
     */
    private UserRepository userRepository;

    /**
     * Build in security context.
     */
    private SecurityContext securityContext;



    /**
     * @param ticketRepository repository for ticket entity
     * @param routeRepository      repository for route entity
     */
    @Inject
    public TicketService(TicketRepository ticketRepository, RouteRepository routeRepository,
                         UserRepository userRepository,  SecurityContext securityContext) {
        this.ticketRepository = ticketRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    /**
     * Finds single ticket.
     *
     * @param id ticket's id
     * @return container with ticket
     */
    @RolesAllowed("ADMIN")//Role.ADMIN) TODO
    public Optional<Ticket> find(Long id) {
        return ticketRepository.find(id);
    }



    /**
     * @return all available tickets
     */
    @RolesAllowed("ADMIN")//Role.ADMIN) TODO
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    /**
     * @return all available tickets of the route
     */
    @RolesAllowed("ADMIN")//Role.ADMIN) TODO
    public Optional<List<Ticket>> findAllByRoute(Long routeId) {
        Optional<Route> route = routeRepository.find(routeId);
        if (route.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ticketRepository.findAllByRoute(route.get()));
        }
    }

    /**
     * Finds single ticket by route
     * @param routeId route's id
     * @param id ticket's id
     * @return
     */
    @RolesAllowed("ADMIN")//Role.ADMIN) TODO
    public Optional<Ticket> findByRoute(Long routeId, Long id) {
        Optional<Route> route = routeRepository.find(routeId);
        if (route.isEmpty()) {
            return Optional.empty();
        } else {
            return ticketRepository.findByRouteAndId(route.get(), id);
        }
    }

    /**
     * @return all available tickets of the authenticated user
     */
    @RolesAllowed("USER")//{Role.ADMIN,Role.USER}) TODO
    public List<Ticket> findAllForCallerPrincipal() {
        return ticketRepository.findAllByUser(
                userRepository.findByEmail(
                        securityContext.getCallerPrincipal().getName()).orElseThrow());
    }

    /**
     * @return ticket of the authenticated user
     */
    @RolesAllowed("USER")//{Role.ADMIN,Role.USER}) TODO
    public Optional<Ticket> findForCallerPrincipal(Long id) {
        return ticketRepository.findByIdAndUser(id,
                userRepository.findByEmail(securityContext.getCallerPrincipal().getName()).orElseThrow());
    }

    /**
     * @return ticket of the authenticated user
     */
    @RolesAllowed("USER")//{Role.ADMIN,Role.USER}) TODO
    public Optional<Ticket> findByRouteForCallerPrincipal(Long routeId, Long id) {
        Optional<Route> route = routeRepository.find(routeId);
        if (route.isEmpty()) {
            return Optional.empty();
        } else {
            return ticketRepository.findByIdAndUserAndRoute(id,
                    userRepository.findByEmail(securityContext.getCallerPrincipal().getName()).orElseThrow(),
                    route.get());
        }
    }

    /**
     * @return ticket of the authenticated user
     */
    @RolesAllowed("USER")//{Role.ADMIN,Role.USER}) TODO
    public Optional<List<Ticket>> findAllByRouteForCallerPrincipal(Long routeId) {
        Optional<Route> route = routeRepository.find(routeId);
        if (route.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(ticketRepository.findAllByUserAndRoute(
                    userRepository.findByEmail(securityContext.getCallerPrincipal().getName()).orElseThrow(),
                    route.get()));
        }
    }

    /**
     * Creates new ticket.
     *
     * @param ticket new ticket
     */
    /*public void create(Ticket ticket) {
        ticketRepository.create(ticket);
        routeRepository.find(ticket.getRoute().getId()).ifPresent(route -> route.getTickets().add(ticket));
    }*/

    @RolesAllowed("USER")//{Role.ADMIN,Role.USER}) TODO
    @LogNewTicket
    public void createForCallerPrincipal(Ticket ticket) {
        User user = userRepository.findByEmail(securityContext.getCallerPrincipal().getName()).orElseThrow();
        ticket.setUser(user);
        ticketRepository.create(ticket);
    }

    /**
     * Updates existing ticket.
     *
     * @param ticket ticket to be updated
     */
    @LogEditTicket
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
    @LogDeleteTicket
    public void delete(Long ticketId) {
        Ticket ticket = ticketRepository.find(ticketId).orElseThrow();
        ticket.getRoute().getTickets().remove(ticket);
        ticketRepository.delete(ticket);
    }



    public Optional<Ticket> findBasedOnCallerPrincipal(Long id) {
        if (securityContext.isCallerInRole(Role.ADMIN.name()))
            return find(id);
        else
            return findForCallerPrincipal(id);
    }


    public List<Ticket> findAllBasedOnCallerPrincipal() {
        if (securityContext.isCallerInRole(Role.ADMIN.name()))
            return findAll();
        else
            return findAllForCallerPrincipal();
    }

    public Optional<List<Ticket>> findAllByRouteBasedOnCallerPrincipal(Long routeId) {
        if (securityContext.isCallerInRole(Role.ADMIN.name()))
            return findAllByRoute(routeId);
        else
            return findAllByRouteForCallerPrincipal(routeId);
    }

    public Optional<Ticket> findByRouteBasedOnCallerPrincipal(Long routeId, Long id) {
        if (securityContext.isCallerInRole(Role.ADMIN.name()))
            return findByRoute(routeId, id);
        else
            return findByRouteForCallerPrincipal(routeId, id);
    }
}

package pl.edu.pg.eti.s176010.airline.route.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.route.repository.RouteRepository;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding ticket's route entity.
 */
@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed("ADMIN")//Role.ADMIN) TODO
public class RouteService {

    /**
     * Repository for route entity.
     */
    private RouteRepository repository;

    /**
     * @param repository repository for route entity
     */
    @Inject
    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    /**
     * @param id id of the route
     * @return container with route entity
     */
    @RolesAllowed({"ADMIN","USER"})//{Role.ADMIN,Role.USER}) TODO
    public Optional<Route> find(Long id) {
        return repository.find(id);
    }


    /**
     * @return container with route entity
     */
    @RolesAllowed({"ADMIN","USER"})//{Role.ADMIN,Role.USER}) TODO
    public List<Route> findAll() {
        return repository.findAll();
    }

    /**
     * Stores new route in the data store.
     *
     * @param route new route to be saved
     */
    public void create(Route route) {
        repository.create(route);
    }

    /**
     * Updates existing route.
     *
     * @param route route to be updated
     */
    public void update(Route route) {
        repository.update(route);
    }

    /**
     * Deletes existing route.
     *
     * @param id existing route's id to be deleted
     */
    public void delete(Long id) {
        repository.delete(repository.find(id).orElseThrow());
    }

}

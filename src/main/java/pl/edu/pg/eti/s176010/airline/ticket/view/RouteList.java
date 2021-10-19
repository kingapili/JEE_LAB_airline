package pl.edu.pg.eti.s176010.airline.ticket.view;


import pl.edu.pg.eti.s176010.airline.ticket.model.RoutesModel;
import pl.edu.pg.eti.s176010.airline.ticket.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * View bean for rendering list of routes.
 */
@RequestScoped
@Named
public class RouteList implements Serializable {

    /**
     * Service for managing routes.
     */
    private final RouteService service;

    /**
     * Service for managing tickets.
     */
    private final TicketService ticketService;

    /**
     * Routes list exposed to the view.
     */
    private RoutesModel routes;

    @Inject
    public RouteList(TicketService ticketService, RouteService service) {
        this.service = service;
        this.ticketService = ticketService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all routes
     */
    public RoutesModel getRoutes() {
        if (routes == null) {
            routes = RoutesModel.entityToModelMapper().apply(service.findAll());
        }
        return routes;
    }

    /**
     * Action for clicking delete action.
     *
     * @param route route to be removed
     * @return navigation case to list_routes
     */
    public String deleteAction(RoutesModel.Route route) {
        ticketService.deleteAllByRoute(route.getId());
        service.delete(route.getId());
        return "route_list?faces-redirect=true";
    }

}

package pl.edu.pg.eti.s176010.airline.route.view;


import pl.edu.pg.eti.s176010.airline.route.model.RoutesModel;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.ejb.EJB;
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
    private RouteService routeService;

    /**
     * Service for managing tickets.
     */
    private TicketService ticketService;

    /**
     * Routes list exposed to the view.
     */
    private RoutesModel routes;


    public RouteList() {
    }

    /**
     * @param routeService service for managing routes
     */
    @EJB
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * @param ticketService service for managing routes
     */
    @EJB
    public void setTicketService(TicketService ticketService) {
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
            routes = RoutesModel.entityToModelMapper().apply(routeService.findAll());
        }
        return routes;
    }

    /**
     * Action for clicking delete action.
     *
     * @param route route to be removed
     * @return navigation case to list_routes
     */
    public void deleteAction(RoutesModel.Route route) {
        routeService.delete(route.getId());
        routes = null;
    }

}

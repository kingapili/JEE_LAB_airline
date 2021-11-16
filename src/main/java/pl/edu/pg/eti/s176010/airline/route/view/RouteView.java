package pl.edu.pg.eti.s176010.airline.route.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.route.model.RouteModel;
import pl.edu.pg.eti.s176010.airline.ticket.model.TicketsModel;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * View bean for rendering list of tickets.
 */
@ViewScoped
@Named
public class RouteView implements Serializable {

    /**
     * Service for managing tickets.
     */
    private TicketService ticketService;

    /**
     * Service for managing tickets.
     */
    private RouteService routeService;

    /**
     * Route id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Route exposed to the view.
     */
    @Getter
    private RouteModel route;

    /**
     * Tickets list exposed to the view.
     */
    private TicketsModel tickets;

    public RouteView() {
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
     * @return all tickets
     */
    public TicketsModel getTickets() {
        if (tickets == null) {
            tickets = TicketsModel.entityToModelMapper().apply(ticketService.findAllByRouteBasedOnCallerPrincipal(id).get());
        }
        return tickets;
    }

    /**
     * Action for clicking delete action.
     *
     * @param ticket ticket to be removed
     * @return navigation case to list_tickets
     */
    public void deleteTicketAction(TicketsModel.Ticket ticket) {
        ticketService.delete(ticket.getId());
        tickets = null;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Route> route = routeService.find(id);
        if (route.isPresent()) {
            this.route = RouteModel.entityToModelMapper().apply(route.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Route not found");
        }
    }

}

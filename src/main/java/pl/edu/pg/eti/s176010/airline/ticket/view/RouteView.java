package pl.edu.pg.eti.s176010.airline.ticket.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.model.RouteModel;
import pl.edu.pg.eti.s176010.airline.ticket.model.TicketModel;
import pl.edu.pg.eti.s176010.airline.ticket.model.TicketsModel;
import pl.edu.pg.eti.s176010.airline.ticket.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.enterprise.context.RequestScoped;
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
    private final TicketService ticketService;

    /**
     * Service for managing tickets.
     */
    private final RouteService routeService;

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

    @Inject
    public RouteView(TicketService ticketService, RouteService routeService) {

        this.ticketService = ticketService;
        this.routeService = routeService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all tickets
     */
    public TicketsModel getTickets() {
        if (tickets == null) {
            tickets = TicketsModel.entityToModelMapper().apply(ticketService.findAllForRoute(id));
        }
        return tickets;
    }

    /**
     * Action for clicking delete action.
     *
     * @param ticket ticket to be removed
     * @return navigation case to list_tickets
     */
    public String deleteTicketAction(TicketsModel.Ticket ticket) {
        ticketService.delete(ticket.getId());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
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

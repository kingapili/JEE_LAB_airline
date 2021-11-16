package pl.edu.pg.eti.s176010.airline.ticket.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.model.TicketEditModel;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * View bean for rendering single ticket edit form.
 */
@ViewScoped
@Named
public class TicketEdit implements Serializable {

    /**
     * Service for managing tickets.
     */
    private TicketService ticketService;

    /**
     * Ticket id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Ticket exposed to the view.
     */
    @Getter
    private TicketEditModel ticket;


    public TicketEdit() {
    }

    /**
     * @param ticketService service for managing routes
     */
    @EJB
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Ticket> ticket = ticketService.findBasedOnCallerPrincipal(id);
        if (ticket.isPresent()) {
            this.ticket = TicketEditModel.entityToModelMapper().apply(ticket.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        ticketService.update(TicketEditModel.modelToEntityUpdater().apply(ticketService.findBasedOnCallerPrincipal(id).orElseThrow(), ticket));
        return "/ticket/ticket_view?faces-redirect=true&includeViewParams=true";
    }

}

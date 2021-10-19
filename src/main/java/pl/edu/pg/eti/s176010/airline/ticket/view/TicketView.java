package pl.edu.pg.eti.s176010.airline.ticket.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.model.TicketModel;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * View bean for rendering single tickets.
 */
@RequestScoped
@Named
public class TicketView implements Serializable {

    /**
     * Service for managing tickets.
     */
    private final TicketService service;

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
    private TicketModel ticket;

    @Inject
    public TicketView(TicketService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Ticket> ticket = service.find(id);
        if (ticket.isPresent()) {
            this.ticket = TicketModel.entityToModelMapper().apply(ticket.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
        }
    }

    
}

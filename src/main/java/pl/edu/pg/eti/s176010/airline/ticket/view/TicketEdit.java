package pl.edu.pg.eti.s176010.airline.ticket.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.model.TicketEditModel;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
    private TicketEditModel ticket;


    @Inject
    public TicketEdit(TicketService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Ticket> ticket = service.find(id);
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
        service.update(TicketEditModel.modelToEntityUpdater().apply(service.find(id).orElseThrow(), ticket));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}

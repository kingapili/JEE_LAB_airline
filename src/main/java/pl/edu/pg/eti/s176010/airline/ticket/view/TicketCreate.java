package pl.edu.pg.eti.s176010.airline.ticket.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.s176010.airline.route.model.RouteModel;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.model.TicketCreateModel;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View bean for rendering single ticket create form. Creating a ticket is divided into number of steps where
 * each step is separate JSF view. In order to use single bean, conversation scope is used.
 */
@RequestScoped
@Named
@NoArgsConstructor
public class TicketCreate implements Serializable {

    /**
     * Service for managing tickets.
     */
    private TicketService service;

    /**
     * Service for managing routes.
     */
    private RouteService routeService;

    /**
     * ticket id.
     */
    @Setter
    @Getter
    private Long id;


    /**
     * Ticket exposed to the view.
     */
    @Getter
    private TicketCreateModel ticket;

    /**
     * Available routes.
     */
    @Getter
    private List<RouteModel> routes;


    @Inject
    public TicketCreate(TicketService service,RouteService routeService, Conversation conversation) {
        this.service = service;
        this.routeService = routeService;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    @PostConstruct
    public void init() {

        routes = routeService.findAll().stream().map(RouteModel.entityToModelMapper())
                .collect(Collectors.toList());
        ticket = TicketCreateModel.builder().build();

    }


    public String saveAction() {
        Ticket newTicket = TicketCreateModel.modelToEntityMapper(
                routeId -> routeService.find(routeId).orElseThrow()).apply(ticket);
        service.create(newTicket);
        return "/ticket/ticket_view.xhtml?faces-redirect=true&id="+newTicket.getId();
    }

}

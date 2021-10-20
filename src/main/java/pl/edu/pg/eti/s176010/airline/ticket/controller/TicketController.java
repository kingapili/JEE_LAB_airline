package pl.edu.pg.eti.s176010.airline.ticket.controller;

import pl.edu.pg.eti.s176010.airline.ticket.dto.GetTicketResponse;
import pl.edu.pg.eti.s176010.airline.ticket.dto.GetTicketsResponse;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * REST controller for {@link Ticket} entity.
 */
@Path("/tickets")
public class TicketController {

    /**
     * Service for managing tickets.
     */
    private TicketService service;

    /**
     * JAX-RS requires no-args constructor.
     */
    public TicketController() {
    }

    /**
     * @param service service for managing tickets
     */
    @Inject
    public void setService(TicketService service) {
        this.service = service;
    }

    /**
     * @return response with available tickets
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTickets() {
        return Response.ok(GetTicketsResponse.entityToDtoMapper().apply(service.findAll())).build();
    }

    /**
     * @param id id of the ticket
     * @return response with selected ticket or 404 status code
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTickets(@PathParam("id") Long id) {
        Optional<Ticket> ticket = service.find(id);
        if (ticket.isPresent()) {
            return Response.ok(GetTicketResponse.entityToDtoMapper().apply(ticket.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}

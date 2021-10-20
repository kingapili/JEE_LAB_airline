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
import java.util.List;
import java.util.Optional;


@Path("/routes/{routeId}/tickets")
public class RouteTicketController {

    /**
     * Service for managing tickets.
     */
    private TicketService ticketService;

    /**
     * JAX-RS requires no-args constructor.
     */
    public RouteTicketController() {
    }

    /**
     * @param ticketService service for managing tickets
     */
    @Inject
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * @param routeId id of the route
     * @return response with available tickets from selected route
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTickets(@PathParam("routeId") Long routeId) {
       List<Ticket> tickets = ticketService.findAllByRoute(routeId);
        if (tickets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(GetTicketsResponse.entityToDtoMapper().apply(tickets)).build();
        }
    }

    /**
     * @param routeId id of the route
     * @param id         id of the ticket
     * @return response with selected ticket or 404 status code
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTickets(@PathParam("routeId") Long routeId, @PathParam("id") Long id) {
        Optional<Ticket> ticket = ticketService.findByRoute(routeId, id);
        if (ticket.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(GetTicketResponse.entityToDtoMapper().apply(ticket.get())).build();
        }
    }

}

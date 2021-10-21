package pl.edu.pg.eti.s176010.airline.ticket.controller;

import pl.edu.pg.eti.s176010.airline.route.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.dto.CreateTicketRequest;
import pl.edu.pg.eti.s176010.airline.ticket.dto.GetTicketResponse;
import pl.edu.pg.eti.s176010.airline.ticket.dto.GetTicketsResponse;
import pl.edu.pg.eti.s176010.airline.ticket.dto.UpdateTicketRequest;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;


@Path("/routes/{routeId}/tickets")
public class RouteTicketController {

    /**
     * Service for managing tickets.
     */
    private TicketService ticketService;

    /**
     * Service for managing tickets.
     */
    private RouteService routeService;

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
     * @param routeService service for managing tickets
     */
    @Inject
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * @param routeId id of the route
     * @return response with available tickets from selected route
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRouteTickets(@PathParam("routeId") Long routeId) {
       Optional<List<Ticket>> tickets = ticketService.findAllByRoute(routeId);
        if (tickets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(GetTicketsResponse.entityToDtoMapper().apply(tickets.get())).build();
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
    public Response getRouteTicket(@PathParam("routeId") Long routeId, @PathParam("id") Long id) {
        Optional<Ticket> ticket = ticketService.findByRoute(routeId, id);
        if (ticket.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(GetTicketResponse.entityToDtoMapper().apply(ticket.get())).build();
        }
    }

    /**
     * Creates new ticket.
     *
     * @param request parsed request body containing info about new ticket
     * @return response with created code and new ticket location url
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRouteTicket(@PathParam("routeId") Long routeId, CreateTicketRequest request) {
        request.setRouteId(routeId);
        Ticket ticket = CreateTicketRequest
                .dtoToEntityMapper(id -> routeService.find(id).orElse(null))
                .apply(request);
        ticketService.create(ticket);
        return Response.created(UriBuilder.fromMethod(RouteTicketController.class, "getRouteTicket")
                .build(ticket.getId())).build();
    }

    /**
     * Updates ticket.
     *
     * @param request parsed request body containing info about ticket
     * @return response with accepted code
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putRouteTicket(@PathParam("routeId") Long routeId, @PathParam("id") Long id,
                                   UpdateTicketRequest request) {
        Optional<Ticket> ticket = ticketService.findByRoute(routeId, id);

        if (ticket.isPresent()) {
            UpdateTicketRequest.dtoToEntityUpdater().apply(ticket.get(), request);

            ticketService.update(ticket.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Delete ticket.
     *
     * @return response with accepted code
     */
    @DELETE
    @Path("{id}")
    public Response deleteRouteTicket(@PathParam("routeId") Long routeId, @PathParam("id") Long id) {
        Optional<Ticket> ticket = ticketService.findByRoute(routeId, id);

        if (ticket.isPresent()) {
            ticketService.delete(ticket.get().getId());
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}

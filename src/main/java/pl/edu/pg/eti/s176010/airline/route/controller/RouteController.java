package pl.edu.pg.eti.s176010.airline.route.controller;

import pl.edu.pg.eti.s176010.airline.route.dto.CreateRouteRequest;
import pl.edu.pg.eti.s176010.airline.route.dto.GetRouteResponse;
import pl.edu.pg.eti.s176010.airline.route.dto.GetRoutesResponse;
import pl.edu.pg.eti.s176010.airline.route.dto.UpdateRouteRequest;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.controller.RouteTicketController;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

/**
 * REST controller for {@link pl.edu.pg.eti.s176010.airline.route.entity.Route} entity.
 */
@Path("/routes")
public class RouteController {

    /**
     * Service for managing routes.
     */
    private RouteService service;

    /**
     * Service for managing routes.
     */
    private TicketService ticketService;

    /**
     * JAX-RS requires no-args constructor.
     */
    public RouteController() {
    }

    /**
     * @param service service for managing routes
     */
    @Inject
    public void setService(RouteService service) {
        this.service = service;
    }

    /**
     * @param ticketService service for managing routes
     */
    @Inject
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * @return response with available routes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoutes() {
        return Response.ok(GetRoutesResponse.entityToDtoMapper().apply(service.findAll())).build();
    }

    /**
     *
     * @param id id of the route
     * @return response with selected route or 404 status code
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response getRoute(@PathParam("id") Long id) {
        Optional<Route> route = service.find(id);
        if (route.isPresent()) {
            return Response.ok(GetRouteResponse.entityToDtoMapper().apply(route.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Creates new route.
     *
     * @param request parsed request body containing info about new route
     * @return response with created code and new route location url
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postRoute(CreateRouteRequest request) {
        Route route = CreateRouteRequest.dtoToEntityMapper().apply(request);
        service.create(route);
        return Response.created(UriBuilder.fromMethod(RouteController.class, "getRoute")
                .build(route.getId())).build();
    }

    /**
     * Updates route.
     *
     * @param request parsed request body containing info about route
     * @return response with accepted code
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putRoute(@PathParam("id") Long id, UpdateRouteRequest request) {
        Optional<Route> route = service.find(id);

        if (route.isPresent()) {
            UpdateRouteRequest.dtoToEntityUpdater().apply(route.get(), request);

            service.update(route.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Delete route.
     *
     * @return response with accepted code
     */
    @DELETE
    @Path("{id}")
    public Response deleteRoute(@PathParam("id") Long id) {
        Optional<Route> route = service.find(id);
        if (route.isPresent()) {
            ticketService.deleteAllByRoute(id);
            service.delete(id);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

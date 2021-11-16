package pl.edu.pg.eti.s176010.airline.route.controller;

import pl.edu.pg.eti.s176010.airline.controller.interceptor.binding.CatchEjbException;
import pl.edu.pg.eti.s176010.airline.route.dto.CreateRouteRequest;
import pl.edu.pg.eti.s176010.airline.route.dto.GetRouteResponse;
import pl.edu.pg.eti.s176010.airline.route.dto.GetRoutesResponse;
import pl.edu.pg.eti.s176010.airline.route.dto.UpdateRouteRequest;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

/**
 * REST controller for {@link pl.edu.pg.eti.s176010.airline.route.entity.Route} entity.
 */
@Path("/routes")
@RolesAllowed("ADMIN")//Role.ADMIN) TODO
@CatchEjbException
public class RouteController {

    /**
     * Service for managing routes.
     */
    private RouteService routeService;

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
    @EJB
    public void setRouteService(RouteService service) {
        this.routeService = service;
    }

    /**
     * @param ticketService service for managing routes
     */
    @EJB
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * @return response with available routes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN","USER"})//{Role.ADMIN,Role.USER}) TODO
    public Response getRoutes() {
        return Response.ok(GetRoutesResponse.entityToDtoMapper().apply(routeService.findAll())).build();
    }

    /**
     *
     * @param id id of the route
     * @return response with selected route or 404 status code
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN","USER"})//{Role.ADMIN,Role.USER}) TODO
    public Response getRoute(@PathParam("id") Long id) {
        Optional<Route> route = routeService.find(id);
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
        routeService.create(route);
        return Response.created(UriBuilder.fromResource(RouteController.class).path(RouteController.class, "getRoute")
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
        Optional<Route> route = routeService.find(id);

        if (route.isPresent()) {
            UpdateRouteRequest.dtoToEntityUpdater().apply(route.get(), request);

            routeService.update(route.get());
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
        Optional<Route> route = routeService.find(id);
        if (route.isPresent()) {
            routeService.delete(id);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

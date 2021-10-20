package pl.edu.pg.eti.s176010.airline.route.controller;

import pl.edu.pg.eti.s176010.airline.route.dto.GetRouteResponse;
import pl.edu.pg.eti.s176010.airline.route.dto.GetRoutesResponse;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
}

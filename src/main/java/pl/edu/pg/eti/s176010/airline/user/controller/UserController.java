package pl.edu.pg.eti.s176010.airline.user.controller;


import pl.edu.pg.eti.s176010.airline.controller.interceptor.binding.CatchEjbException;
import pl.edu.pg.eti.s176010.airline.user.dto.CreateUserRequest;
import pl.edu.pg.eti.s176010.airline.user.dto.GetUserResponse;
import pl.edu.pg.eti.s176010.airline.user.dto.GetUsersResponse;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.service.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

/**
 * REST controller for {@link pl.edu.pg.eti.s176010.airline.user.entity.User} entity.
 */
@Path("")
@RolesAllowed("ADMIN")//Role.ADMIN) TODO
@CatchEjbException
public class UserController {

    /**
     * Service for managing users.
     */
    private UserService service;

    /**
     * JAX-RS requires no-args constructor.
     */
    public UserController() {
    }

    /**
     * @param service service for managing users
     */
    @EJB
    public void setService(UserService service) {
        this.service = service;
    }

//    /**
//     * @return response with available users
//     */
//    @GET
//    @Path("/users")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getUsers() {
//        return Response
//                .ok(GetUsersResponse.entityToDtoMapper().apply(service.findAll()))
//                .build();
//    }
//
//    /**
//     * @param id user's id
//     * @return response with selected user
//     */
//    @GET
//    @Path("/users/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getUser(@PathParam("id") Long id) {
//        Optional<User> user = service.find(id);
//        if (user.isPresent()) {
//            return Response
//                    .ok(GetUserResponse.entityToDtoMapper().apply(user.get()))
//                    .build();
//        } else {
//            return Response
//                    .status(Response.Status.NOT_FOUND)
//                    .build();
//        }
//    }

    /**
     * @return response with logged user or empty object
     */
    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN","USER"})//{Role.ADMIN,Role.USER}) TODO
    public Response getUser() {
        Optional<User> user = service.findCallerPrincipal();
        if (user.isPresent()) {
            return Response
                    .ok(GetUserResponse.entityToDtoMapper().apply(user.get()))
                    .build();
        } else {
            return Response
                    .ok(new Object())
                    .build();
        }
    }


    @Path("/users")
    @POST
    @PermitAll
    public Response createUser(CreateUserRequest request) {
        User user = CreateUserRequest.dtoToEntityMapper().apply(request);
        service.create(user);
        return Response.created(UriBuilder.fromPath("/users/{id}")
                .build(user.getId())).build();
    }

}

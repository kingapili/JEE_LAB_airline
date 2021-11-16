package pl.edu.pg.eti.s176010.airline.user.servlet;

import pl.edu.pg.eti.s176010.airline.controller.interceptor.binding.CatchEjbException;
import pl.edu.pg.eti.s176010.airline.servlet.ServletUtility;
import pl.edu.pg.eti.s176010.airline.user.dto.GetUserResponse;
import pl.edu.pg.eti.s176010.airline.user.dto.GetUsersResponse;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet for returning user's name from the active session if present.
 */
@WebServlet(urlPatterns = UserServlet.Paths.USER + "/*")
@RolesAllowed("ADMIN")//Role.USER) TODO
public class UserServlet extends HttpServlet {
    /**
     * Service for user entity operations.
     */
    private UserService userService;


    public UserServlet(){
    }

    /**
     * @param userService service for managing users
     */
    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static class Paths {

        /**
         * Specified portrait for download and upload.
         */
        public static final String USER = "/api_servlet/users";

    }

    public static class Patterns {

        /**
         * All users.
         */
        public static final String USERS = "^/?$";

        /**
         * Specified ticket.
         */
        public static final String USER = "^/[0-9]+/?$";

    }

    /**
     * JSON-B mapping object. According to open liberty documentation creating those is expensive. The JSON-B is only
     * one of many solutions. JSON strings can be build by hand {@link StringBuilder} or with JSON-P API. Both JSON-B
     * and JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        try {
            if (path.matches(Patterns.USER)) {
                getUser(request, response);
            } else if (path.matches(Patterns.USERS)) {
                getUsers(request, response);
            }
        } catch (EJBAccessException ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(userService.findAll())));
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));

        response.setContentType("application/json");
        if (id != null) {
            Optional<User> user = userService.find(id);
            if (user.isPresent()) {
                response.getWriter().write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
                return;
            }
        }
        response.getWriter().write("{}");//Empty JSON object.
    }
}

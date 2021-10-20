package pl.edu.pg.eti.s176010.airline.configuration;

import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Listener started automatically on servlet context initialized. Fetches instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch database datasource and init data only
 * in cases of empty database. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@ApplicationScoped
public class InitializedData {

    /**
     * Service for tickets operations.
     */
    private final TicketService ticketService;

    /**
     * Service for users operations.
     */
    private final UserService userService;

    /**
     * Service for routes operations.
     */
    private final RouteService routeService;

    @Inject
    public InitializedData(TicketService ticketService, UserService userService, RouteService routeService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.routeService = routeService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should
     * be created only once.
     *
     */
    private synchronized void init() {
        //users
        User admin = User.builder()
                .id(0L)
                .name("Kinga Adminowska")
                .birthDate(LocalDate.of(1999, 1, 7))
                .email("admin@airline.example.com")
                .role(Role.ADMIN)
                .avatarFileName(null)
                .build();

        User ryszard = User.builder()
                .id(1L)
                .name("Ryszard Pietruszka")
                .birthDate(LocalDate.of(1983, 7, 22))
                .email("piet@example.com")
                .role(Role.USER)
                .avatarFileName(null)
                .build();

        User janina = User.builder()
                .id(2L)
                .name("Janina Kowalska")
                .birthDate(LocalDate.of(1979, 3, 30))
                .email("j.kowal@example.com")
                .role(Role.USER)
                .avatarFileName(null)
                .build();

        User wojciech = User.builder()
                .id(3L)
                .name("Wojciech Okruszek")
                .birthDate(LocalDate.of(1996, 11, 22))
                .email("wokru@example.com")
                .role(Role.USER)
                .avatarFileName(null)
                .build();

        userService.create(admin);
        userService.create(ryszard);
        userService.create(janina);
        userService.create(wojciech);

        //routes
        Route gda_waw = Route.builder()
                .id(1L)
                .distance(100)
                .duration(75)
                .startingPoint("Gdańsk")
                .destination("Warszawa")
                .build();

        Route cra_pra = Route.builder()
                .id(2L)
                .distance(156)
                .duration(45)
                .startingPoint("Karków")
                .destination("Praga")
                .build();

        Route lon_ber = Route.builder()
                .id(3L)
                .distance(300)
                .duration(60)
                .startingPoint("London")
                .destination("Berlin")
                .build();

        routeService.create(gda_waw);
        routeService.create(cra_pra);
        routeService.create(lon_ber);

        //tickets
        Ticket gda_waw1 = Ticket.builder()
                .id(1L)
                .dateTime(LocalDateTime.of(2021,10,30,10,30))
                .cost(75.6)
                .route(gda_waw)
                .build();

        //tickets
        Ticket gda_waw2 = Ticket.builder()
                .id(2L)
                .dateTime(LocalDateTime.of(2021,8,13,10,30))
                .cost(120.99)
                .route(gda_waw)
                .build();

        ticketService.create(gda_waw1);
        ticketService.create(gda_waw2);

    }

}

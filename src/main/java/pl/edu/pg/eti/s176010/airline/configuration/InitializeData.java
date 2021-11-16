package pl.edu.pg.eti.s176010.airline.configuration;

import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Listener started automatically on servlet context initialized. Fetches instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch database datasource and init data only
 * in cases of empty database. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@Singleton
@Startup
public class InitializeData {

    /**
     * Password hashing algorithm.
     */
    private Pbkdf2PasswordHash pbkdf;

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Inject
    public void setPbkdf(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    public InitializeData() {
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should
     * be created only once.
     *
     */
    @PostConstruct
    private synchronized void init() {
        //users
        User admin = User.builder()
                .name("Kinga Adminowska")
                .birthDate(LocalDate.of(1999, 1, 7))
                .email("admin@airline.example.com")
                .roles(List.of(Role.ADMIN))
                .password(pbkdf.generate("adminadmin".toCharArray()))
                .avatarFileName(null)
                .build();

        User ryszard = User.builder()
                .name("Ryszard Pietruszka")
                .birthDate(LocalDate.of(1983, 7, 22))
                .email("piet@example.com")
                .roles(List.of(Role.USER))
                .password(pbkdf.generate("useruser".toCharArray()))
                .avatarFileName(null)
                .build();

        User janina = User.builder()
                .name("Janina Kowalska")
                .birthDate(LocalDate.of(1979, 3, 30))
                .email("j.kowal@example.com")
                .roles(List.of(Role.USER))
                .password(pbkdf.generate("useruser".toCharArray()))
                .avatarFileName(null)
                .build();

        User wojciech = User.builder()
                .name("Wojciech Okruszek")
                .birthDate(LocalDate.of(1996, 11, 22))
                .email("wokru@example.com")
                .roles(List.of(Role.USER))
                .password(pbkdf.generate("useruser".toCharArray()))
                .avatarFileName(null)
                .build();

        em.persist(admin);
        em.persist(ryszard);
        em.persist(janina);
        em.persist(wojciech);

        //routes
        Route gda_waw = Route.builder()
                .distance(100)
                .duration(75)
                .startingPoint("Gdańsk")
                .destination("Warszawa")
                .build();

        Route cra_pra = Route.builder()
                .distance(156)
                .duration(45)
                .startingPoint("Karków")
                .destination("Praga")
                .build();

        Route lon_ber = Route.builder()
                .distance(300)
                .duration(60)
                .startingPoint("London")
                .destination("Berlin")
                .build();

        em.persist(gda_waw);
        em.persist(cra_pra);
        em.persist(lon_ber);

        //tickets
        Ticket gda_waw1 = Ticket.builder()
                .dateTime(LocalDateTime.of(2021,10,30,10,30))
                .cost(75.6)
                .route(gda_waw)
                .user(janina)
                .build();

        //tickets
        Ticket gda_waw2 = Ticket.builder()
                .dateTime(LocalDateTime.of(2021,8,13,10,30))
                .cost(120.99)
                .route(gda_waw)
                .user(wojciech)
                .build();

        em.persist(gda_waw1);
        em.persist(gda_waw2);

        gda_waw.setTickets(List.of(gda_waw1,gda_waw2));
    }

}

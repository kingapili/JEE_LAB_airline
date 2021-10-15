package pl.edu.pg.eti.s176010.airline.configuration;

import pl.edu.pg.eti.s176010.airline.user.entity.Role;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;

/**
 * Listener started automatically on servlet context initialized. Fetches instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch database datasource and init data only
 * in cases of empty database. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@ApplicationScoped
public class InitializedData {

    /**
     * Service for users operations.
     */
    private final UserService userService;

    @Inject
    public InitializedData(UserService userService) {
        this.userService = userService;
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
    }

}

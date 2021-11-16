package pl.edu.pg.eti.s176010.airline.ticket.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Entity for ticket owned by the user. Contains link to user (see @link {@link User}) for the sake of database relationship.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name="tickets")
public class Ticket implements Serializable {

    /**
     * Ticket's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ticket's cost.
     */
    private double cost;

    /**
     * Ticket's plane route.
     */
    @ManyToOne
    @JoinColumn(name = "route")
    private Route route;

    /**
     * Owner of this ticket.
     */
    @ManyToOne
    @JoinColumn(name ="user")
    private User user;

    /**
     * Date for which ticket is bought.
     */
    @Column(name = "date_time")
    private LocalDateTime dateTime;

}

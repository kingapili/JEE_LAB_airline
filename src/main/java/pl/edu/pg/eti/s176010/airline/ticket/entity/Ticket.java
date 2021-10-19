package pl.edu.pg.eti.s176010.airline.ticket.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

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
public class Ticket implements Serializable {

    /**
     * Ticket's id.
     */
    private Long id;

    /**
     * Ticket's cost.
     */
    private double cost;

    /**
     * Ticket's plane route.
     */
    private Route route;

    /**
     * Owner of this ticket.
     */
    //private User user;

    /**
     * Date for which ticket is bought.
     */
    private LocalDateTime dateTime;

}

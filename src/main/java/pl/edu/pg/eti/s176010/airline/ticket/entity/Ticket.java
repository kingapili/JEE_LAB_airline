package pl.edu.pg.eti.s176010.airline.ticket.entity;

import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * Entity for ticket owned by the user. Contains link to user (see @link {@link User}) for the sake of database relationship.
 */
public class Ticket implements Serializable {

    /**
     * Ticket's id.
     */
    private String id;

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
    private User user;

    /**
     * Date for which ticket is bought.
     */
    private LocalDate date;

}

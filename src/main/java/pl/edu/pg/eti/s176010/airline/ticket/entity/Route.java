package pl.edu.pg.eti.s176010.airline.ticket.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Entity for plane route.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Route implements Serializable {

    /**
     * Route's id
     */
    private Long id;

    /**
     * Route's destination.
     */
    private String destination;

    /**
     * Route's starting point.
     */
    private String startingPoint;

    /**
     * Route's duration in minutes.
     */
    private int duration;

    /**
     * Route's distance in kilometers.
     */
    private double distance;

}

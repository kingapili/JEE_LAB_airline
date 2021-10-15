package pl.edu.pg.eti.s176010.airline.route.entity;

import java.io.Serializable;

/**
 * Entity for plane route.
 */
public class Route implements Serializable {

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

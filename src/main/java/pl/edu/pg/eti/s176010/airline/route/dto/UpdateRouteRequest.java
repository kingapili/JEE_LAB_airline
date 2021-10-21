package pl.edu.pg.eti.s176010.airline.route.dto;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateRouteRequest {

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

    /**
     * @return updater for convenient updating entity object using dto object
     */
    public static BiFunction<Route, UpdateRouteRequest, Route> dtoToEntityUpdater() {
        return (route, request) -> {
            route.setDestination(request.getDestination());
            route.setStartingPoint(request.getStartingPoint());
            route.setDistance(request.getDistance());
            route.setDuration(request.getDuration());
            return route;
        };
    }
}

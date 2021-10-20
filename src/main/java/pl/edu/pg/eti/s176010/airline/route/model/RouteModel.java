package pl.edu.pg.eti.s176010.airline.route.model;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;

import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents single route to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RouteModel {

    /**
     * Id of the route.
     */
    private Long id;

    /**
     * Name of the route.
     */
    private String name;

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
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Route, RouteModel> entityToModelMapper(){
        return route -> RouteModel.builder()
                .id(route.getId())
                .name(route.getStartingPoint()+'-'+route.getDestination())
                .destination(route.getDestination())
                .startingPoint(route.getStartingPoint())
                .duration(route.getDuration())
                .distance(route.getDistance())
                .build();
    }
}

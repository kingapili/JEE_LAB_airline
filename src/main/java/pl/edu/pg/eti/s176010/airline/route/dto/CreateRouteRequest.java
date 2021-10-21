package pl.edu.pg.eti.s176010.airline.route.dto;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;


import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateRouteRequest {

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
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<CreateRouteRequest, Route> dtoToEntityMapper() {
        return request -> Route.builder()
                .destination(request.getDestination())
                .startingPoint(request.getStartingPoint())
                .duration(request.getDuration())
                .distance(request.getDistance())
                .build();
    }

}

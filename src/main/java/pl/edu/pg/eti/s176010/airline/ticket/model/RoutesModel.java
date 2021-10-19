package pl.edu.pg.eti.s176010.airline.ticket.model;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents list of routes to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RoutesModel implements Serializable {

    /**
     * Represents single route in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Route {

        /**
         * Unique id identifying route.
         */
        private Long id;

        /**
         * Name of the route.
         */
        private String name;

    }

    /**
     * Name of the selected routes.
     */
    @Singular
    private List<Route> routes;

    /**
     * @return mapper for convenient converting entity object to model object
     */
    public static Function<Collection<pl.edu.pg.eti.s176010.airline.ticket.entity.Route>, RoutesModel> entityToModelMapper() {
        return routes -> {
            RoutesModel.RoutesModelBuilder model = RoutesModel.builder();
            routes.stream()
                    .map(route -> RoutesModel.Route.builder()
                            .id(route.getId())
                            .name(RouteModel.entityToModelMapper().apply(route).getName())
                            .build())
                    .forEach(model::route);
            return model.build();
        };
    }

}

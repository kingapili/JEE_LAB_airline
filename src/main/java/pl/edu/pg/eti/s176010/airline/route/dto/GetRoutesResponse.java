package pl.edu.pg.eti.s176010.airline.route.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * GET routes response. Returns list of all available routes names.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetRoutesResponse {

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
     * Ids and names of selected routes.
     */
    @Singular
    private List<Route> routes;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<pl.edu.pg.eti.s176010.airline.route.entity.Route>, GetRoutesResponse> entityToDtoMapper() {
        return routes -> {
            GetRoutesResponse.GetRoutesResponseBuilder response = GetRoutesResponse.builder();
            routes.stream()
                    .map(route -> GetRoutesResponse.Route.builder()
                            .id(route.getId())
                            .name(GetRouteResponse.entityToDtoMapper().apply(route).getName())
                            .build())
                    .forEach(response::route);
            return response.build();
        };
    }
}

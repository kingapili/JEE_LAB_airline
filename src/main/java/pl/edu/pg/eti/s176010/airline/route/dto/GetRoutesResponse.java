package pl.edu.pg.eti.s176010.airline.route.dto;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;

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
     * List of all routes ids.
     */
    @Singular
    private List<Long> routes;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<Route>, GetRoutesResponse> entityToDtoMapper() {
        return routes -> {
            GetRoutesResponseBuilder response = GetRoutesResponse.builder();
            routes.stream()
                    .map(Route::getId)
                    .forEach(response::route);
            return response.build();
        };
    }
}

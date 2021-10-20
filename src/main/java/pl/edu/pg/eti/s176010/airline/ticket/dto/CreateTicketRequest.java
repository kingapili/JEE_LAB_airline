package pl.edu.pg.eti.s176010.airline.ticket.dto;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateTicketRequest {

    /**
     * Ticket's cost.
     */
    private double cost;

    /**
     * Date for which ticket is bought.
     */
    private LocalDateTime dateTime;

    /**
     * Ticket's route id.
     */
    private Long routeId;

    /**
     * @param routeFunction function for converting route id to route entity object
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<CreateTicketRequest, Ticket> dtoToEntityMapper(
            Function<Long, Route> routeFunction) {
        return request -> Ticket.builder()
                .cost(request.getCost())
                .dateTime(request.getDateTime())
                .route(routeFunction.apply(request.getRouteId()))
                .build();
    }

}

package pl.edu.pg.eti.s176010.airline.ticket.model;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Route;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents new ticket to be created. Includes oll
 * fields which can be use in ticket creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class TicketCreateModel {

    /**
     * Ticket's cost.
     */
    private double cost;

    /**
     * Date for which ticket is bought.
     */
    private LocalDateTime dateTime;

    /**
     * Ticket's route.
     */
    private Long route;

    /**
     * @param routeFunction function for converting route name to route entity object
     * @return mapper for convenient converting model object to entity object
     */
    public static Function<TicketCreateModel, Ticket> modelToEntityMapper(
            Function<Long, Route> routeFunction) {
        return model -> Ticket.builder()
                .cost(model.getCost())
                .dateTime(model.dateTime)
                .route(routeFunction.apply(model.getRoute()))
                .build();
    }

}

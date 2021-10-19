package pl.edu.pg.eti.s176010.airline.ticket.model;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import java.time.*;
import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents single ticket to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class TicketModel {

    /**
     * Ticket's id.
     */
    private Long id;

    /**
     * Ticket's cost.
     */
    private double cost;

    /**
     * Ticket's plane route name.
     */
    private String routeName;

    /**
     * Ticket's plane route id.
     */
    private Long routeId;

    /**
     * Date for which ticket is bought.
     */
    private LocalDate date;

    /**
     * Time for which ticket is bought.
     */
    private LocalTime time;

    /**
     * Timezone offset for which ticket is bought.
     */
    private ZoneOffset zoneOffSet;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Ticket, TicketModel> entityToModelMapper() {
        return ticket -> TicketModel.builder()
                .id(ticket.getId())
                .cost(ticket.getCost())
                .routeName(RouteModel.entityToModelMapper().apply(ticket.getRoute()).getName())
                .routeId(ticket.getRoute().getId())
                .date(ticket.getDateTime().toLocalDate())
                .time(ticket.getDateTime().toLocalTime())
                .zoneOffSet(ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now()))
                .build();
    }
}

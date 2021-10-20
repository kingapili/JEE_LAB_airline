package pl.edu.pg.eti.s176010.airline.ticket.dto;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.route.dto.GetRouteResponse;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetTicketResponse {

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
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Ticket, GetTicketResponse> entityToDtoMapper() {
        return ticket -> GetTicketResponse.builder()
                .id(ticket.getId())
                .cost(ticket.getCost())
                .routeName(GetRouteResponse.entityToDtoMapper().apply(ticket.getRoute()).getName())
                .routeId(ticket.getRoute().getId())
                .date(ticket.getDateTime().toLocalDate())
                .time(ticket.getDateTime().toLocalTime())
                .build();
    }

}

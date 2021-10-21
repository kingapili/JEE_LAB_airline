package pl.edu.pg.eti.s176010.airline.ticket.dto;

import lombok.*;
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
public class UpdateTicketRequest {

    /**
     * Date for which ticket is bought.
     */
    private LocalDateTime dateTime;

    /**
     * Ticket's cost.
     */
    private double cost;

    /**
     * @return updater for convenient updating entity object using dto object
     */
    public static BiFunction<Ticket, UpdateTicketRequest, Ticket> dtoToEntityUpdater() {
        return (ticket, request) -> {
            ticket.setDateTime(request.getDateTime());
            ticket.setCost(request.getCost());
            return ticket;
        };
    }
}

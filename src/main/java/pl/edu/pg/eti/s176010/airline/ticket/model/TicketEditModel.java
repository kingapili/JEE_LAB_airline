package pl.edu.pg.eti.s176010.airline.ticket.model;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import java.time.*;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents single ticket to be edited. Includes
 * only fields which can be edited after ticket creation.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class TicketEditModel {

    /**
     * Date for which ticket is bought.
     */
    private LocalDateTime datetime;


    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Ticket, TicketEditModel> entityToModelMapper() {
        return ticket -> TicketEditModel.builder()
                .datetime(ticket.getDateTime())
                .build();
    }

    /**
     * @return updater for convenient updating entity object using model object
     */
    public static BiFunction<Ticket, TicketEditModel, Ticket> modelToEntityUpdater() {
        return (ticket, request) -> {
            ticket.setDateTime(request.datetime);
            return ticket;
        };
    }

}

package pl.edu.pg.eti.s176010.airline.ticket.model;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * JSF view model class in order to not to use entity classes. Represents list of tickets to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class TicketsModel implements Serializable {

    /**
     * Represents single ticket in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Ticket {

        /**
         * Unique id identifying ticket.
         */
        private Long id;

    }

    /**
     * Name of the selected tickets.
     */
    @Singular
    private List<Ticket> tickets;

    /**
     * @return mapper for convenient converting entity object to model object
     */
    public static Function<Collection<pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket>, TicketsModel> entityToModelMapper() {
        return tickets -> {
            TicketsModelBuilder model = TicketsModel.builder();
            tickets.stream()
                    .map(ticket -> Ticket.builder()
                            .id(ticket.getId())
                            .build())
                    .forEach(model::ticket);
            return model.build();
        };
    }

}

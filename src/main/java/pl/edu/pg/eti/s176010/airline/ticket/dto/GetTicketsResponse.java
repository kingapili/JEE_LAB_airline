package pl.edu.pg.eti.s176010.airline.ticket.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * GET tickets response. Contains list of available tickets. Can be used to list particular user's tickets as
 * well as all tickets in the game.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetTicketsResponse {

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

        /**
         * dateTime of the ticket.
         */
        private LocalDateTime dateTime;

    }

    /**
     * Name of the selected tickets.
     */
    @Singular
    private List<Ticket> tickets;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket>, GetTicketsResponse> entityToDtoMapper() {
        return tickets -> {
            GetTicketsResponseBuilder response = GetTicketsResponse.builder();
            tickets.stream()
                    .map(ticket -> Ticket.builder()
                            .id(ticket.getId())
                            .dateTime(ticket.getDateTime())
                            .build())
                    .forEach(response::ticket);
            return response.build();
        };
    }

}

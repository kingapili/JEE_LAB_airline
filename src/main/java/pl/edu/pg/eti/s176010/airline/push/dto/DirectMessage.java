package pl.edu.pg.eti.s176010.airline.push.dto;

import lombok.*;

/**
 * WebSocket message representation.
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class DirectMessage {

    /**
     * Message author.
     */
    private String from;

    /**
     * Message author.
     */
    private String to;

    /**
     * Message content.
     */
    private String content;

}

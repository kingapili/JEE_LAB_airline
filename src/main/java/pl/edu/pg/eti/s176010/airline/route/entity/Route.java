package pl.edu.pg.eti.s176010.airline.route.entity;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Entity for plane route.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "routes")
public class Route implements Serializable {

    /**
     * Route's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Route's destination.
     */
    private String destination;

    /**
     * Route's starting point.
     */
    @Column(name = "starting_point")
    private String startingPoint;

    /**
     * Route's duration in minutes.
     */
    private int duration;

    /**
     * Route's distance in kilometers.
     */
    private double distance;

    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE)
    private List<Ticket> tickets;

}

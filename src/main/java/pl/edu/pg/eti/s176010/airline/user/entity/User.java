package pl.edu.pg.eti.s176010.airline.user.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;

/**
 * Entity for system user.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    /**
     * User's id.
     */
    private Long id;

    /**
     * User's contact email.
     */
    private String email;

    /**
     * User's given name.
     */
    private String name;

    /**
     * User's birthdate.
     */
    private LocalDate birthDate;

    /**
     * User's role.
     */
    private Role role;

    /**
     * User's avatar.
     */
    private String avatarFileName;

}

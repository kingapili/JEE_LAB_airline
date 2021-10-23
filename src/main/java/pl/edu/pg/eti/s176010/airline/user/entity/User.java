package pl.edu.pg.eti.s176010.airline.user.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name = "users")
public class User implements Serializable {

    /**
     * User's id.
     */
    @Id
    private Long id;

    /**
     * User's contact email.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * User's given name.
     */
    private String name;

    /**
     * User's birthdate.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * User's role.
     */
    private Role role;

    /**
     * User's avatar.
     */
    @Column(name = "avatar_file_name")
    private String avatarFileName;

}

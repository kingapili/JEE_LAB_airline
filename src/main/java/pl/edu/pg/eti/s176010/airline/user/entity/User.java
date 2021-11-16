package pl.edu.pg.eti.s176010.airline.user.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * User's security roles.
     */
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private List<Role> roles;

    /**
     * User's password.
     */
    @ToString.Exclude
    private String password;

    /**
     * User's avatar.
     */
    @Column(name = "avatar_file_name")
    private String avatarFileName;

}

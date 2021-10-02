package pl.edu.pg.eti.s176010.airline.user.entity;


import java.time.LocalDate;

/**
 * Entity for system user.
 */

public class User {

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

}

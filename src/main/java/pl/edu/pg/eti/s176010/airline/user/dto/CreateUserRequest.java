package pl.edu.pg.eti.s176010.airline.user.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * PSOT user request. Contains only fields that can be set during user creation. User is defined in
 * {@link pl.edu.pg.eti.s176010.airline.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

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

}

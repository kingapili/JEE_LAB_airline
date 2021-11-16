package pl.edu.pg.eti.s176010.airline.user.dto;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

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
     * User's given password.
     */
    private String password;

    /**
     * User's birthdate.
     */
    private LocalDate birthDate;

    /**
     * @return mapper for convenient converting dto object to entity object
     */
    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return request -> User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .password(request.getPassword())
                .build();
    }

}

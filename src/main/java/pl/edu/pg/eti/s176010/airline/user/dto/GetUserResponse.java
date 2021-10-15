package pl.edu.pg.eti.s176010.airline.user.dto;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * GET user response. Contains only field's which can be displayed on frontend. User is defined in
 * {@link pl.edu.pg.eti.s176010.airline.user.entity.User}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {

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

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .avatarFileName(user.getAvatarFileName())
                .build();
    }

}

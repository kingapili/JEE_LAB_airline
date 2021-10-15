package pl.edu.pg.eti.s176010.airline.user.dto;

import lombok.*;
import pl.edu.pg.eti.s176010.airline.user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * GET users response. Contains users' names of users in the system.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUsersResponse {

    /**
     * Represents single user in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class User {
        /**
         * Unique id.
         */
        private Long id;

        /**
         * Unique email.
         */
        private String email;

        /**
         * Name of the users.
         */
        private String name;

    }

    /**
     * Name of the selected users.
     */
    @Singular
    private List<User> users;

    /**
     * @return mapper for convenient converting entity object to dto object
     */
    public static Function<Collection<pl.edu.pg.eti.s176010.airline.user.entity.User>, GetUsersResponse> entityToDtoMapper() {
        return users -> {
            GetUsersResponse.GetUsersResponseBuilder response = GetUsersResponse.builder();
            users.stream()
                    .map(user -> User.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .name(user.getName())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }

}

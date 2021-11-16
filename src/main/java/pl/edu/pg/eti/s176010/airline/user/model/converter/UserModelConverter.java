package pl.edu.pg.eti.s176010.airline.user.model.converter;

import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.model.UserModel;
import pl.edu.pg.eti.s176010.airline.user.service.UserService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

@FacesConverter(forClass = UserModel.class, managed = true)
public class UserModelConverter implements Converter<UserModel> {

    /**
     * Service for user management.
     */
    private UserService service;

    @Inject
    public UserModelConverter(UserService service) {
        this.service = service;
    }

    @Override
    public UserModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<User> user = service.find(value);
        return user.isEmpty() ? null : UserModel.entityToModelMapper().apply(user.get());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, UserModel value) {
        return value == null ? "" : value.getEmail();
    }

}

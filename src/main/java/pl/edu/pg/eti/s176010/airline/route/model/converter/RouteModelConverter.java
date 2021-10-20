package pl.edu.pg.eti.s176010.airline.route.model.converter;

import pl.edu.pg.eti.s176010.airline.route.entity.Route;
import pl.edu.pg.eti.s176010.airline.route.model.RouteModel;
import pl.edu.pg.eti.s176010.airline.route.service.RouteService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Faces converter for {@link RouteModel}. The managed attribute in {@link @FacesConverter} allows the converter
 * to be the CDI bean. In previous version of JSF converters were always created inside JSF lifecycle and where not
 * managed by container that is injection was not possible. As this bean is not annotated with scope the beans.xml
 * descriptor must be present.
 */
@FacesConverter(forClass = RouteModel.class, managed = true)
public class RouteModelConverter implements Converter<RouteModel> {

    /**
     * Route for routes management.
     */
    private RouteService service;

    @Inject
    public RouteModelConverter(RouteService service) {
        this.service = service;
    }

    @Override
    public RouteModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Long id = Long.parseLong(value);
        Optional<Route> route = service.find(id);
        return route.isEmpty() ? null : RouteModel.entityToModelMapper().apply(route.get());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, RouteModel value) {
        return value == null ? "" : value.getId().toString();
    }

}

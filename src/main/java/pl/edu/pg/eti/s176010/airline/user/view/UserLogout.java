package pl.edu.pg.eti.s176010.airline.user.view;

import lombok.SneakyThrows;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * View bean for handling form logout.
 */
@RequestScoped
@Named
public class UserLogout {

    /**
     * Current HTTP request.
     */
    private HttpServletRequest request;

    @Inject
    public UserLogout(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Action initiated by clicking logout button.
     *
     * @return navigation case to the same page
     */
    @SneakyThrows
    public String logoutAction() {
        request.logout();
        return "route_list?faces-redirect=true&includeViewParams=true";
    }

}

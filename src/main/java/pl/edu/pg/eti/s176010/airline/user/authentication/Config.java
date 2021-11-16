package pl.edu.pg.eti.s176010.airline.user.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 * Configuration class for security context. There are three authentication mechanism and only one can be used at a time:
 * <ul>
 *     <li>{@link BasicAuthenticationMechanismDefinition} every secured resource is secured with basic auth mechanism,
 *     ideal for REST services (JAX-RS and Servlet). No login forms work but rest services can be used from clients.</li>
 * </ul>
 */
@ApplicationScoped
//@BasicAuthenticationMechanismDefinition(realmName = "Simple Airline portal")
@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/authentication/form/login.xhtml",
                errorPage = "/authentication/form/login_error.xhtml"
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/SimpleAirlineTicketPortal",
        callerQuery = "select password from users where email = ?",
        groupsQuery = "select role from users_roles join users on users_roles.user = users.id where users.email = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class Config {
}

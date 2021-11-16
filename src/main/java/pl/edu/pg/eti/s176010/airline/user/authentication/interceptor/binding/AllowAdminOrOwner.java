package pl.edu.pg.eti.s176010.airline.user.authentication.interceptor.binding;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Binding for interceptor allowing only admin or user owning the character.
 */
@InterceptorBinding
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AllowAdminOrOwner {
}

package pl.edu.pg.eti.s176010.airline.controller.interceptor.binding;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Binding for interceptor catching EJB exceptions and changing to HTTP error codes.
 */
@InterceptorBinding
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CatchEjbException {
}

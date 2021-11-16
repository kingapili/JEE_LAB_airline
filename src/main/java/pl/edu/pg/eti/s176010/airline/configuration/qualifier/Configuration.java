package pl.edu.pg.eti.s176010.airline.configuration.qualifier;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines single configuration entry.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
public @interface Configuration {

    /**
     * @return name of the configuration entry (key)
     */
    @Nonbinding
    String value() default "";

}

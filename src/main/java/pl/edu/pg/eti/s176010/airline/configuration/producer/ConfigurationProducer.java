package pl.edu.pg.eti.s176010.airline.configuration.producer;

import pl.edu.pg.eti.s176010.airline.configuration.qualifier.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.util.Properties;

/**
 * Producer for configuration entries. It reads config form default properties file and delivers configuration entries
 * into injection points.
 */
@ApplicationScoped
public class ConfigurationProducer {

    /**
     * @param point describes injection point, point where dependency will be delivered
     * @return configuration entry, value to be injected
     */
    @Produces
    @Configuration
    public String createConfiguration(InjectionPoint point) {
        String key = point.getAnnotated().getAnnotation(Configuration.class).value();
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/pl/edu/pg/eti/kask/rpg/configuration/application.properties"));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        return properties.getProperty(key);
    }

}

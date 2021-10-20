package pl.edu.pg.eti.s176010.airline.route.repository;

import pl.edu.pg.eti.s176010.airline.datastore.DataStore;
import pl.edu.pg.eti.s176010.airline.repository.Repository;
import pl.edu.pg.eti.s176010.airline.route.entity.Route;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Repository for route entity. Repositories should be used in business layer (e.g.: in services).
 */
@Dependent
public class RouteRepository implements Repository<Route, Long> {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public RouteRepository(DataStore store) {
        this.store = store;
    }


    @Override
    public Optional<Route> find(Long id) {
        return store.findRoute(id);
    }

    @Override
    public List<Route> findAll() {
        return store.findAllRoutes();
    }

    @Override
    public void create(Route entity) {
        store.createRoute(entity);
    }

    @Override
    public void delete(Route entity) {
        store.deleteRoute(entity.getId());
    }

    @Override
    public void update(Route entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

}

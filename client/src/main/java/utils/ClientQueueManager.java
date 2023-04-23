package utils;

import data.CollectionContainer;
import data.Route;
import queueManager.PriorityQueueManageable;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ClientQueueManager implements PriorityQueueManageable {
    private Route routeArgument = null;
    public Route getRouteArgument() { return routeArgument; }
    @Override
    public void add(Route route) { routeArgument = route; }
    @Override
    public boolean addRouteIfMin(Route route) {
        routeArgument = route;
        return false;
    }
    @Override
    public boolean addRouteIfMax(Route route) {
        routeArgument = route;
        return false;
    }
    @Override
    public long countLessThanDistance(double distance) { return 0; }
    @Override
    public long countGreaterThanDistance(double distance) { return 0; }
    @Override
    public List<Route> filterLessThanDistance(double distance) { return Collections.emptyList(); }
    @Override
    public void updateById(int id, Route newRoute) throws NoSuchElementException { routeArgument = newRoute; }
    @Override
    public boolean containsId(int id) { return false; }
    @Override
    public void save() throws IOException {}
    @Override
    public Route getHead() { return null; }
    @Override
    public void clear() {}
    @Override
    public List<Route> getQueue() { return Collections.emptyList(); }
    @Override
    public CollectionContainer getContainer() { return null; }
    @Override
    public void removeById(int id) throws NoSuchElementException {}
    @Override
    public Map<Integer, Route> getIds() { return null; }
}

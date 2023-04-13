package queueManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.CollectionContainer;
import data.Route;
import fileIO.interfaces.DataReader;
import fileIO.interfaces.DataWriter;
import utils.RouteFilter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class PriorityQueueManager implements PriorityQueueManageable {
    private CollectionContainer container;
    private final Map<Integer, Route> ids;
    private final DataWriter dataWriter;
    private final String filePath;

    /**
     * Class that manages collection
     * @param reader reader to use to get collection from file
     * @param writer writer to use to write collection to file
     * @param filename filename / filepath where json file with collection is stored and could be written to
     * @throws FileNotFoundException throws if file with given filename is not found
     * @throws JsonProcessingException throws if file was found, but the content could not be parsed to CollectionContainer
     */
    public PriorityQueueManager(DataReader reader, DataWriter writer, String filename)
            throws FileNotFoundException, JsonProcessingException {
        dataWriter = writer;
        filePath = filename;
        try {
            container = reader.decodeQueueFromFile(filename);
        } catch (NoSuchElementException e) {
            container = new CollectionContainer(new Route[0], LocalDate.now());
        }
        ids = container.getQueue().stream().collect(Collectors.toMap(Route::getId, route -> route));
    }
    public void add(Route route) {
        container.getQueue().add(route);
        ids.put(route.getId(), route);
    }
    public boolean addRouteIfMin(Route route) {
        if (container.getQueue().isEmpty()) {
            container.getQueue().add(route);
            return true;
        }
        if (container.getQueue().stream().min(RouteFilter.DISTANCE_LESS).get().getDistance() > route.getDistance()) {
            container.getQueue().add(route);
            return true;
        }
        return false;
    }
    public boolean addRouteIfMax(Route route) {
        if (container.getQueue().isEmpty()) {
            container.getQueue().add(route);
            return true;
        }
        if (container.getQueue().stream().max(RouteFilter.DISTANCE_GREATER).get().getDistance() < route.getDistance()) {
            container.getQueue().add(route);
            return true;
        }
        return false;
    }
    public long countLessThanDistance(double distance) {
        return container.getQueue().stream()
                .filter(route -> route.getDistance() < distance)
                .count();
    }
    public long countGreaterThanDistance(double distance) {
        return container.getQueue().stream()
                .filter(route -> route.getDistance() > distance)
                .count();
    }
    public List<Route> filterLessThanDistance(double distance) {
        return container.getQueue().stream()
                .filter(route -> route.getDistance() < distance)
                .sorted(RouteFilter.BY_NAME)
                .toList();
    }
    public void updateById(int id, Route newRoute) throws NoSuchElementException {
        if (!ids.containsKey(id)) {
            throw new NoSuchElementException();
        }
        container.getQueue().remove(ids.replace(id, newRoute));
        container.getQueue().add(newRoute);
    }
    public boolean containsId(int id)  {
        return ids.containsKey(id);
    }
    public void save() throws IOException {
        dataWriter.encodeQueueToFile(filePath, container);
    }
    public Route getHead() {
        return container.getQueue().peek();
    }
    public void clear() {
        ids.clear();
        container.getQueue().clear();
    }
    public List<Route> getQueue() {
        return container.getQueue().stream()
                .sorted(RouteFilter.BY_NAME)
                .toList();
    }
    public CollectionContainer getContainer() {
        return container;
    }
    public void removeById(int id) throws NoSuchElementException {
        if (!ids.containsKey(id)) throw new NoSuchElementException();
        container.getQueue().remove(ids.remove(id));
    }
    public Map<Integer, Route> getIds() {
        return ids;
    }
}

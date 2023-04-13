package queueManager;

import data.CollectionContainer;
import data.Route;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface PriorityQueueManageable {
    /**
     * adds Route to queue
     * @see commands.Add#execute(String)
     * @param route route to add
     */
    public void add(Route route);

    /**
     * adds route to file if its distance is less than any other in collection
     * @see commands.AddIfMin#execute(String)
     * @param route route to add
     * @return true if the element was less than minimum, and false if it wasn't, so element wasn't added
     */
    public boolean addRouteIfMin(Route route);

    /**
     * adds route to file if its distance is greater than any other in collection
     * @see commands.AddIfMax#execute(String)
     * @param route route to add
     * @return true if the element was greater than maximum, and false if it wasn't, so element wasn't added
     */
    public boolean addRouteIfMax(Route route);

    /**
     * @see commands.LessThanDistance#execute(String)
     * @param distance distance to compare with
     * @return amount of elements whose distance is less than given distance
     */
    public long countLessThanDistance(double distance);

    /**
     * @see commands.GreaterThanDistance#execute(String)
     * @param distance distance to compare with
     * @return amount of elements whose distance is greater than given distance
     */
    public long countGreaterThanDistance(double distance);

    /**
     * @see commands.FilterLessThanDistance#execute(String)
     * @param distance distance to compare with
     * @return list of elements whose distance is less than the given one
     */
    public List<Route> filterLessThanDistance(double distance);

    /**
     * updates element in the collection by the given id
     * @see commands.Update#execute(String)
     * @param id id of desired element
     * @param newRoute new element to replace with
     * @throws NoSuchElementException throws if there is no element with the given id in the collection
     */
    public void updateById(int id, Route newRoute) throws NoSuchElementException;

    /**
     * helper function for checking if container has element with given id
     * @param id id to search for
     * @return true if container has element with given id, false for opposite
     */
    public boolean containsId(int id);

    /**
     * saves current collection to file
     * @see commands.Save#execute(String)
     * @throws IOException throws if could not save to file due to access problems, for example
     */
    public void save() throws IOException;

    /**
     * @see commands.Head#execute(String)
     * @return Route from head of the collection; does not remove it
     */
    public Route getHead();

    /**
     * clears current collection
     * @see commands.Clear#execute(String)
     */
    public void clear();

    /**
     * helper function
     * @return queue stored in collection
     */
    public List<Route> getQueue();

    /**
     * helper function
     * @return current container
     */
    public CollectionContainer getContainer();

    /**
     * removes element by given id
     * @see commands.RemoveById#execute(String)
     * @param id id of element to be removed
     * @throws NoSuchElementException throws if there is no element with given id in collection
     */
    public void removeById(int id) throws NoSuchElementException;

    /**
     * helper function
     * @return map with current ids
     */
    public Map<Integer, Route> getIds();
}

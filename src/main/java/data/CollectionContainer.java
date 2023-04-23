package data;

import utils.RouteFilter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CollectionContainer {
    public interface CollectionFunction<R,T> {
        T update(R queue) throws SQLException;
    }
    public interface CollectionConsumer<R> {
        void apply(R queue) throws SQLException;
    }
    private final Collection<Route> queue;
    private final LocalDate creationDate;

    /**
     * Container class that stores routes and metadata about the json file
     * @param routes routes to store and restore
     */
    public CollectionContainer(List<Route> routes) {
        this.queue = Collections.synchronizedCollection(new PriorityQueue<>(RouteFilter.BY_NAME));
        this.queue.addAll(routes);
        this.creationDate = LocalDate.now();
    }

    /**
     * @return length of the stored collection
     */
    public long getCollectionLength() {
        return queue.size();
    }

    /**
     * @return metadata about creation date of the json file
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * @return metadata about collection type stored in container
     */
    public String getQueueType() {
        return queue.getClass().getSimpleName();
    }

    /**
     * @return queue stored in container
     */
    public void updateQueue(CollectionConsumer<PriorityQueue<Route>> function) throws SQLException {
        PriorityQueue<Route> priorityQueue = new PriorityQueue<>(queue);
        function.apply(priorityQueue);
        commitChangesInQueue(priorityQueue);
    }
    public <T> T updateQueue(CollectionFunction<PriorityQueue<Route>, ? extends T> function) throws SQLException {
        PriorityQueue<Route> priorityQueue = new PriorityQueue<>(queue);
        T result = function.update(priorityQueue);
        commitChangesInQueue(priorityQueue);
        return result;
    }
    private void commitChangesInQueue(Collection<Route> newCollection) {
        // delete all elements, that were deleted
        queue.removeIf(route -> !newCollection.contains(route));
        // idk why is this working
//        while (iterator.hasNext()) {
//            Route route = iterator.next();
//            if (!newCollection.contains(route)) {
//                iterator.remove();
//            }
//        }

        // and this is not (????!!!!!)
//        queue.stream()
//                .filter(route -> !newCollection.contains(route))
//                .forEach(queue::remove);
        // and add all elements, that were added
        newCollection.stream()
                .filter(route -> !queue.contains(route))
                .forEach(queue::add);
    }

}

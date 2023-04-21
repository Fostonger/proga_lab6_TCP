package data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.RouteFilter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class CollectionContainer {
    private PriorityQueue<Route> queue;
    private LocalDate creationDate;

    /**
     * Container class that stores routes and metadata about the json file
     * @param routes routes to store and restore
     */
    @JsonCreator
    public CollectionContainer(
            @JsonProperty("routes") List<Route> routes
    ) {
        this.queue = (PriorityQueue<Route>) Collections.synchronizedCollection(new PriorityQueue<>(RouteFilter.BY_NAME));
        this.queue.addAll(routes);
        this.creationDate = LocalDate.now();
    }

    /**
     * @return length of the stored collection
     */
    @JsonIgnore
    public long getCollectionLength() {
        return queue.size();
    }

    /**
     * @return metadata about creation date of the json file
     */
    @JsonProperty("creationDate")
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * @return metadata about collection type stored in container
     */
    @JsonIgnore
    public String getQueueType() {
        return queue.getClass().getSimpleName();
    }

    /**
     * @return queue stored in container
     */
    @JsonProperty("routes")
    public PriorityQueue<Route> getQueue() {
        return queue;
    }
}

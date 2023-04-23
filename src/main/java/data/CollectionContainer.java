package data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.RouteFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.PriorityQueue;

public class CollectionContainer {
    private PriorityQueue<Route> queue;
    private LocalDate creationDate;

    /**
     * Container class that stores routes and metadata about the json file
     * @param routes routes to store and restore
     * @param creationDate metadata about creation date of the json file
     */
    @JsonCreator
    public CollectionContainer(
            @JsonProperty("routes") Route[] routes,
            @JsonProperty("creationDate") LocalDate creationDate
    ) {
        this.queue = new PriorityQueue<Route>(RouteFilter.DISTANCE_LESS);
        this.queue.addAll(List.of(routes));
        this.creationDate = creationDate;
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

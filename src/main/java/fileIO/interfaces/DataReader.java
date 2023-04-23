package fileIO.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.CollectionContainer;

import java.io.FileNotFoundException;

public interface DataReader {
    /**
     * decodes CollectionContainer from file by given filepath
     * @param path filepath where to find the file with collection
     * @return CollectionContainer decoded from file
     * @throws JsonProcessingException throws if file was found, but data inside did not match with CollectionContainer
     * @throws FileNotFoundException throws if file with given path was not found
     */
    public CollectionContainer decodeQueueFromFile(String path) throws JsonProcessingException, FileNotFoundException;
}

package fileIO.interfaces;

import data.CollectionContainer;

import java.io.IOException;

public interface DataWriter {
    /**
     * encodes CollectionContainer to file by given filepath
     * @param path filepath where the data will be written
     * @param container container to be encoded
     * @throws IOException throws if file was not found or could not be written to for any other reasons
     */
    public void encodeQueueToFile(String path, CollectionContainer container) throws IOException;
}

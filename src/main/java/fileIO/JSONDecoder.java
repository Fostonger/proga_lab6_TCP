package fileIO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.CollectionContainer;
import fileIO.interfaces.DataReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class that reads from JSON file and parses its values
 */
public class JSONDecoder implements DataReader {
    /**
     * @param path Path of the file to read from
     * @return Value of type T stored in JSON
     */
    public CollectionContainer decodeQueueFromFile(String path) throws JsonProcessingException, FileNotFoundException {
        Scanner jsonScanner = new Scanner(new File(path));
        String jsonString = jsonScanner.useDelimiter("\\Z").next();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(jsonString, CollectionContainer.class);
    }
}

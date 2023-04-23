package fileIO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.CollectionContainer;
import fileIO.interfaces.DataWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JSONEncoder implements DataWriter {
    private final ObjectMapper objectMapper;
    public JSONEncoder() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Override
    public void encodeQueueToFile(String path, CollectionContainer container) throws IOException {
        objectMapper.writeValue(new BufferedWriter(new FileWriter(path)), container);
    }
}

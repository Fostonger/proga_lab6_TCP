package utils;

import java.io.IOException;
import java.io.Serializable;

public interface ClientNetWorkable extends ResponseGettable, RequestSendable {
    <T extends Serializable> String sendRequestAndGetResponse(T o) throws IOException, ClassNotFoundException;
}

package utils;

import java.io.IOException;

public interface ClientNetWorkable extends ResponseGettable, RequestSendable {
    String sendRequestAndGetResponse(Object o) throws IOException, ClassNotFoundException;
}

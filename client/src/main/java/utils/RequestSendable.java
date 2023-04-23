package utils;

import java.io.IOException;
import java.io.Serializable;

public interface RequestSendable {
    <T extends Serializable> void sendObject(T o);
}

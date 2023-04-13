package data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private int x;
    private Integer y;

    /**
     * Base Coordinates class that stored inside of Route class
     * @param x nonnull int x of the class
     * @param y nonnull int x of the class, has to be not greater than 106
     */
    @JsonCreator
    public Coordinates(@JsonProperty("x") int x, @JsonProperty("y") Integer y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public Integer getY() { return y; }
    /**
     * overridden method to use in need of printing the coordinates
     * @return printable string representation of the coordinates
     */
    @Override
    public String toString() {
        return "\tx:\t\t\t\t" + x + "\n" +
                "\ty:\t\t\t\t" + y;
    }
}

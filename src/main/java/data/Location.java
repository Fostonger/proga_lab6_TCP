package data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Location implements Serializable {
    private Long x; //Поле не может быть null
    private Integer y; //Поле не может быть null
    private double z;
    private String name; //Поле может быть null

    /**
     * Base Location class that stored inside of Route class
     * @param x nonnull long x of the location
     * @param y nonnull int y of the location
     * @param z nonnull double z of the location
     * @param name nullable name of the location
     */
    @JsonCreator
    public Location(
            @JsonProperty("x") Long x, @JsonProperty("y") Integer y,
            @JsonProperty("z") double z, @JsonProperty("name") String name) {
        this.x = x;
        this.name = name;
        this.z = z;
        this.y = y;
    }
    public String getName() {
        return name;
    }
    public Integer getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public Long getX() {
        return x;
    }
    /**
     * overridden method to use in need of printing the location
     * @return printable string representation of the location
     */
    @Override
    public String toString() {
        return "\tx:\t\t\t\t" + x + "\n" +
                "\ty:\t\t\t\t" + y + "\n" +
                "\tz:\t\t\t\t" + z + "\n" +
                "\tname:\t\t\t" + ((name != null) ? name : "null");
    }
}

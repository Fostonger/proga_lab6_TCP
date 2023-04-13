package consoleReader;

import data.Coordinates;
import data.Location;

public interface PropertiesReceivable {
    /**
     * reads Location from input
     * @param message message to be printed before input. Use Location parameter name to convey to the user what to set
     * @param isNullable can the Location parameter be null
     * @return new Location instance if all nonnull parameters are set, and null if one of nonnull parameters is null while isNullable parameter is set to true
     */
    public Location getLocationFromInput(String message, boolean isNullable);
    /**
     * reads Coordinates from input
     * @param message message to be printed before input. Use Coordinates parameter name to convey to the user what to set
     * @return new Coordinates instance
     */
    public Coordinates getCoordinatesFromInput(String message);
    /**
     * reads String from input
     * @param message message to be printed before input. Use String parameter name to convey to the user what to set
     * @return user typed String, or null if empty string was entered
     */
    public String getStringFromInput(String message);
    /**
     * reads String from input
     * @param message message to be printed before input. Use String parameter name to convey to the user what to set
     * @return user typed String
     */
    public String getNotNullStringFromInput(String message);
    /**
     * reads double from input
     * @param message message to be printed before input. Use this double parameter name to convey to the user what to set
     * @return user typed double, that is bigger than 1
     */
    public double getDoubleBiggerThanOneFromInput(String message);
}

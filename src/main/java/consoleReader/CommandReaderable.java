package consoleReader;

public interface CommandReaderable {
    /**
     * reads the string from input
     * @return String got from input
     */
    public String getString();
    /**
     * reads the int from input
     * @return int got from input
     */
    public int getInt();
    /**
     * reads the double from input
     * @return double got from input
     */
    public double getDouble();
    /**
     * reads the long from input
     * @return long got from input
     */
    public long getLong();
    public boolean hasNext();
}

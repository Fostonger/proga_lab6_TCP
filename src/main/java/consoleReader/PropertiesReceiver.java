package consoleReader;

import data.Coordinates;
import data.Location;

public class PropertiesReceiver implements PropertiesReceivable {
    private final CommandReaderable reader;
    private final OutputWritable outputWriter;

    /**
     * receiver that reads from given source and returns instances of desired classes 
     * @param reader source to read from
     * @param writer output stream to write what to enter
     */
    public PropertiesReceiver(CommandReaderable reader, OutputWritable writer) {
        this.outputWriter = writer;
        this.reader = reader;
    }
    public Location getLocationFromInput(String message, boolean isNullable) {
        if (message != null && outputWriter != null) outputWriter.writeMessage(message);
        if (!isNullable) {
            long x = getNotNullLongFromInput("\tx:\t\t");
            int y = getNotNullIntFromInput("\ty:\t\t");
            double z = getNotNullDoubleFromInput("\tz:\t\t");
            String name = getStringFromInput("\tname:\t");
            return new Location(x, y, z, name);
        }
        Long x = getLongFromInput("\tx:\t\t");
        if (x == null) return null;
        Integer y = getIntFromInput("\ty:\t\t");
        if (y == null) return null;
        double z = getNotNullDoubleFromInput("\tz:\t\t");
        String name = getStringFromInput("\tname:\t");
        return new Location(x, y, z, name);
    }

    @Override
    public Coordinates getCoordinatesFromInput(String message) {
        if (message != null && outputWriter != null) outputWriter.writeMessage(message);
        int x = getNotNullIntFromInput("\tx:\t\t");
        int y = getIntLessThan106("\ty:\t\t");
        return new Coordinates(x,y);
    }
    @Override
    public String getStringFromInput(String message) {
        if (message != null && outputWriter != null) outputWriter.writeMessage(message);
        String stringFromInput = reader.getString();
        return (!stringFromInput.equals("")) ? stringFromInput : null;
    }
    public String getNotNullStringFromInput(String message) {
        if (message != null && outputWriter != null) outputWriter.writeMessage(message);
        String stringFromInput = getRightInput(ReceiverRules.NOT_NULL,
                  message + "cannot be null!\n", message);
        return stringFromInput;
    }
    public double getNotNullDoubleFromInput(String name) {
        if (name != null && outputWriter != null) outputWriter.writeMessage(name);
        String doubleString = getRightInput(ReceiverRules.NOT_NULL_DOUBLE,
                name + "cannot be null and has to be number!\n", name);
        return Double.parseDouble(doubleString);
    }
    public double getDoubleBiggerThanOneFromInput(String name) {
        if (name != null && outputWriter != null) outputWriter.writeMessage(name);
        String doubleString = getRightInput(ReceiverRules.DOUBLE_MIN_1,
                name + "cannot be null and has to be greater than 1!\n", name);
        return Double.parseDouble(doubleString);
    }
    private int getIntLessThan106(String name) {
        if (name != null && outputWriter != null) outputWriter.writeMessage(name);
        String intString = getRightInput(ReceiverRules.INT_MIN_106,
                name + "cannot be null and has to be less than 106!\n", name);
        return Integer.parseInt(intString);
    }
    private int getNotNullIntFromInput(String name) {
        if (name != null && outputWriter != null) outputWriter.writeMessage(name);
        String intString = getRightInput(ReceiverRules.NOT_NULL_INT,
                name + "cannot be null and has to be number!\n", name);
        return Integer.parseInt(intString);
    }
    private long getNotNullLongFromInput(String name) {
        if (name != null && outputWriter != null) outputWriter.writeMessage(name);
        String longString = getRightInput(ReceiverRules.NOT_NULL_LONG,
                name + "cannot be null and has to be number!\n", name);
        return Long.parseLong(longString);
    }
    private Long getLongFromInput(String name) {
        if (name != null && outputWriter != null) outputWriter.writeMessage(name);
        String longString = getRightInput(ReceiverRules.NULLABLE_LONG,
                name + "has to be number!\n", name);
        if (longString == null || longString.equals("")) return null;
        return Long.parseLong(longString);
    }
    private Integer getIntFromInput(String name) throws NumberFormatException {
        if (name != null && outputWriter != null) outputWriter.writeMessage(name);
        String intString = getRightInput(ReceiverRules.NULLABLE_INT,
                name + "has to be number!\n", name);
        if (intString == null || intString.equals("")) return null;
        return Integer.parseInt(intString);
    }

    /**
     * input reader, loops over while input that fits specified rules is not given
     * pretty much the same as getThrowableInput, but catches the throws
     * @see PropertiesReceiver#getThrowableInput(ReceiverRules, String, String) 
     * @param rule rule to check for
     * @param propertyRuleDescription rule of the parameter to be printed when inappropriate value was read
     * @param propertyName name of the property written before the input to convey user what to write
     * @return String that fitted the given rule, or null if empty string was passed, and it fitted the rule
     */
    private String getRightInput(ReceiverRules rule, String propertyRuleDescription, String propertyName) {
        String line;
        while(true) {
            try {
                line = getThrowableInput(rule, propertyRuleDescription, propertyName);
                break;
            } catch (NumberFormatException e) {
                if (outputWriter != null) {
                    outputWriter.writeMessage(propertyRuleDescription);
                    outputWriter.writeMessage(propertyName);
                }
            }
        }
        return line;
    }

    /**
     * throwable input reader, loops over while input that fits specified rules is not given
     * @param rule rule to check for
     * @param propertyRuleDescription rule of the parameter to be printed when inappropriate value was read
     * @param propertyName name of the property written before the input to convey user what to write
     * @return String that fitted the given rule, or null if empty string was passed, and it fitted the rule
     * @throws NumberFormatException throws if the input string could not be converted to desired type
     */
    private String getThrowableInput(ReceiverRules rule, String propertyRuleDescription, String propertyName)
            throws NumberFormatException {
        String line = reader.getString();
        while(true) {
            try {
                if (rule.isAcceptable(line)) break;
                if (outputWriter != null) {
                    outputWriter.writeMessage(propertyRuleDescription);
                    outputWriter.writeMessage(propertyName);
                }
                line = reader.getString();
            } catch (NumberFormatException e) {
                if (line.equals("") && rule.isAcceptable(null)) return null;
                throw e;
            }
        }
        return line;
    }
}

package consoleReader;

public interface ReceiverRuleInterface {
    /**
     * function to check if object fits the rule
     * @param o1 object to be processed
     * @return true if object can be accepted by given rule, false for opposite
     */
    public boolean isAcceptable(Object o1);
}

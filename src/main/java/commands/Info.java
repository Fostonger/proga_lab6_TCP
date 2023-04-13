package commands;

import data.CollectionContainer;

/**
 * command that returns string with information about collection, such as creation date, collection type and collection size
 */
public class Info extends AbstractCommand {
    private final CollectionContainer container;

    /**
     * @param container to read metadata from
     */
    public Info(CollectionContainer container) {
        super("info","info", "prints information about saved collection");
        this.container = container;
    }

    @Override
    public String execute(String arg) {
        if (container == null) return "program was passed incorrect container!\n";
        if (arg.equals("")) {
            String infoString = "creation date:\t\t\t\t" + container.getCreationDate().toString() + "\n" +
                    "type of collection stored:\t" + container.getQueueType() + "\n" +
                    "collection length:\t\t\t" + container.getCollectionLength() + "\n";
            return infoString;
        } else {
            return "command was ran with incorrect argument, see 'help' for information\n";
        }
    }
}

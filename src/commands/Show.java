package commands;

import data.Route;

import java.util.PriorityQueue;

/**
 * command that returns string with all elements' description
 */
public class Show extends AbstractCommand {
    private final PriorityQueue<Route> queue;

    /**
     * @param queue queue whose elements' descriptions will be returned
     */
    public Show(PriorityQueue<Route> queue) {
        super("show","show", "prints all elements of the collection to standard output");
        this.queue = queue;
    }

    @Override
    public String execute(String arg) {
        if (arg.equals("")) {
            StringBuilder showString = new StringBuilder();
            Route[] routes = queue.toArray(new Route[0]);
            for (int i = 0; i < queue.size(); i++) {
                showString.append("--------- Element ").append(i + 1).append(" ---------\n");
                showString.append(routes[i].toString());
            }
            return (routes.length > 0) ? showString.toString() : "collection is empty!\n";
        } else {
            return "command was ran with incorrect argument, see 'help' for information\n";
        }
    }
}

package commands;

import queueManager.PriorityQueueManageable;
import consoleReader.CommandReaderable;
import consoleReader.OutputWritable;
import utils.RouteCreatable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * interpreter and executor of the commands
 */
public class CommandInterpreter {
    private final Map<String, AbstractCommand> commands;
    private final CommandReaderable commandReader;
    private final OutputWritable outputWriter;

    /**
     * @param queueManager manager to be used in commands
     * @param routeFactory routes creator to be used in commands that need routes to be given
     * @param commandReader reader of the commands
     * @param outputWriter stream to write commands result
     */
    public CommandInterpreter(PriorityQueueManageable queueManager, RouteCreatable routeFactory,
                              CommandReaderable commandReader, OutputWritable outputWriter) {
        this.commandReader = commandReader;
        this.outputWriter = outputWriter;
        commands = new HashMap<>();

        Add add = new Add(queueManager, routeFactory);
        commands.put(add.getName(), add);

        Save save = new Save(queueManager);
        commands.put(save.getName(), save);

        RemoveById removeById = new RemoveById(queueManager);
        commands.put(removeById.getName(), removeById);

        Help help = new Help(commands.values());
        commands.put(help.getName(), help);

        GreaterThanDistance greaterThanDistance = new GreaterThanDistance(queueManager);
        commands.put(greaterThanDistance.getName(), greaterThanDistance);

        LessThanDistance lessThanDistance = new LessThanDistance(queueManager);
        commands.put(lessThanDistance.getName(), lessThanDistance);

        FilterLessThanDistance filterLessThanDistance = new FilterLessThanDistance(queueManager);
        commands.put(filterLessThanDistance.getName(), filterLessThanDistance);

        AddIfMax addIfMax = new AddIfMax(queueManager, routeFactory);
        commands.put(addIfMax.getName(), addIfMax);

        AddIfMin addIfMin = new AddIfMin(queueManager, routeFactory);
        commands.put(addIfMin.getName(), addIfMin);

        Info info = new Info(queueManager.getContainer());
        commands.put(info.getName(), info);

        Show show = new Show(queueManager.getContainer().getQueue());
        commands.put(show.getName(), show);

        Update update = new Update(queueManager, routeFactory);
        commands.put(update.getName(), update);

        Clear clear = new Clear(queueManager);
        commands.put(clear.getName(), clear);

        Exit exit = new Exit(outputWriter);
        commands.put(exit.getName(), exit);

        Head head = new Head(queueManager);
        commands.put(head.getName(), head);

        ExecuteScript exec = new ExecuteScript(queueManager, outputWriter);
        commands.put(exec.getName(), exec);
    }

    /**
     * fetches command from input stream and executes the command if it was called correctly
     */
    public void fetchCommand() {
        String commandString = commandReader.getString();
        String commandName;
        String argName;
        Pattern commandNamePat = Pattern.compile("^\\w+\\s*");
        Pattern argNamePat = Pattern.compile("^.+");
        Matcher matcher = commandNamePat.matcher(commandString);

        if (matcher.find()) {
            commandName = matcher.group().trim();
        } else {
            outputWriter.writeMessage("wrong command! try writing 'help' command to see all available variants\n");
            return;
        }
        AbstractCommand command = commands.get(commandName);
        if (command == null) {
            outputWriter.writeMessage("wrong command! try writing 'help' command to see all available variants\n");
            return;
        }

        commandString = commandString.substring(commandName.length());

        matcher = argNamePat.matcher(commandString);
        argName = matcher.find() ? matcher.group().trim() : "";

        outputWriter.writeMessage(command.execute(argName));
    }
}

package Commands;

import Parser.ParsedCommand;
import utils.CommandUtils;
import java.util.List;
import java.util.Map;

public class TypeCommand implements Command {
    private Map<String, Command> commands;

    public TypeCommand(Map<String, Command> supportedCommands) {
        commands = supportedCommands;
    }

    @Override
    public void execute(ParsedCommand parsedCommand) {
        for(String command: parsedCommand.args){
            if(commands.containsKey(command)){
                commands.get(command).type();
            }else {
                if(!checkInPath(command)){
                    System.out.println(command+": not found");
                }
            }
        }
    }

    @Override
    public void type() {
        System.out.println("type is a shell builtin");
    }

    private boolean checkInPath(String command) {

        List<String> commandLocations = CommandUtils.checkCommandInPaths(command);

        if (!commandLocations.isEmpty()) {
            for (String location : commandLocations) {
                System.out.println(command+" is "+location);
            }
            return true;
        } else {
            return false;
        }
    }
}

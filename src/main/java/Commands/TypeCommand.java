package Commands;

import Parser.ParsedCommand;
import utils.CommandUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeCommand implements Command {
    private Map<String, Command> commands;
    private List<String> pathDirectories;

    public TypeCommand(Map<String, Command> supportedCommands) {
        String path = System.getenv("PATH");
        List<String> pathDirectories = Arrays.stream(path.split(":")).collect(Collectors.toList());
        commands = supportedCommands;
        this.pathDirectories = pathDirectories;
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

    private boolean checkInPath(String command) {

        List<String> commandLocations = CommandUtils.checkCommandInPaths(command, pathDirectories);

        if (!commandLocations.isEmpty()) {
            for (String location : commandLocations) {
                System.out.println(command+" is "+location);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void type() {
        System.out.println("type is a shell builtin");
    }
}

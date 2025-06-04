package Commands;

import Parser.ParsedCommand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeCommand implements Command {
    private Map<String, Command> commands;
    private List<String> pathDirectories;
    public TypeCommand(Map<String, Command> supportedCommands) {
        commands = supportedCommands;
    }

    public TypeCommand(Map<String, Command> supportedCommands, List<String> pathDirectories) {
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
        List<String> commandLocations = new ArrayList<>();

        for (String dir : this.pathDirectories) {
            File lsFile = new File(dir, command); // Check for "ls" in this directory
            if (lsFile.exists() && lsFile.canExecute()) {
                commandLocations.add(lsFile.getAbsolutePath());
            }
        }

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

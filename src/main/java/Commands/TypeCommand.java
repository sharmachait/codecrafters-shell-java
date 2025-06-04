package Commands;

import Parser.ParsedCommand;

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
            }else{
                System.out.println("-bash: type: "+command+": not found");
            }
        }
    }

    @Override
    public void type() {
        System.out.println("type is a shell builtin");
    }
}

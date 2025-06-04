package Commands;

import Parser.ParsedCommand;

public class CatCommand implements Command {
    @Override
    public void execute(ParsedCommand parsedCommand) {

    }

    @Override
    public void type() {
        System.out.println("cat is a shell builtin");
    }
}

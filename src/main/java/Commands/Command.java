package Commands;

import Parser.ParsedCommand;

public interface Command {
//    void execute();
    void execute(ParsedCommand parsedCommand);
    void type();
//    String execute(int statusCode);
}

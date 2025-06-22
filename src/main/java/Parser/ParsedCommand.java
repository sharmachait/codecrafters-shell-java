package Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ParsedCommand {
    public String command;
    public List<String> args;

    public static ParsedCommand fromInput(String line) {
        ParsedCommand res = new ParsedCommand();
        String[] parts = line.split(" ");
        res.command = parts[0];
        res.args = Arrays.asList(parts).subList(1, parts.length);
        return res;
    }
}
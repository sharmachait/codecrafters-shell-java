package Parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static ParsedCommand readCommand(String line){

        if(line.isEmpty()) {
            return new ParsedCommand();
        }

        ParsedCommand parsedCommand = new ParsedCommand();
        List<String> arguments = new ArrayList<>();

        int spaceIndex = line.indexOf(' ');
        if(spaceIndex == -1) {
            // No arguments, just a command
            parsedCommand.command = line;
            parsedCommand.args = new String[0];
            return parsedCommand;
        }

        parsedCommand.command = line.substring(0, spaceIndex);

        int i = spaceIndex + 1;
        while(i < line.length()){
            //skip all whitespaces
            while(i < line.length() && line.charAt(i) == ' ') {
                i++;
            }
            if(i >= line.length()) break;

            // Build a complete argument (may consist of multiple parts)
            StringBuilder argumentBuilder = new StringBuilder();

            // Keep parsing until we hit a space or end of line
            while(i < line.length() && line.charAt(i) != ' ') {
                char curr = line.charAt(i);
                if(curr == '\'') {
                    // Parse quoted string
                    i++; // Skip opening quote
                    while(i < line.length() && line.charAt(i) != '\'') {
                        argumentBuilder.append(line.charAt(i));
                        i++;
                    }
                    if(i < line.length()) {
                        i++; // Skip closing quote
                    }
                } else {
                    // Parse unquoted characters until space or quote
                    while(i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\'') {
                        argumentBuilder.append(line.charAt(i));
                        i++;
                    }
                }
            }

            arguments.add(argumentBuilder.toString());
        }

//        System.out.println(arguments);

        parsedCommand.args = arguments.toArray(new String[arguments.size()]);
        return parsedCommand;
    }
}

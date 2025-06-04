package server;

import Commands.Command;
import Parser.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ShellServer {
    public static void start(Map<String, Command> supportedCommands) {
        String path = System.getenv("PATH");
        List<String> pathDirectories = Arrays.stream(path.split(":")).collect(Collectors.toList());
        while(true){
            System.out.print("$ ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            ParsedCommand command = Parser.readCommand(line);
            CommandHandler.handleCommand(command, pathDirectories, supportedCommands);
        }
    }
}

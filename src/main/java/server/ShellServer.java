package server;

import Commands.Command;
import Parser.*;
import java.util.Map;
import java.util.Scanner;

public class ShellServer {
    public static void start(Map<String, Command> supportedCommands) {
        while(true){
            System.out.print("$ ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            ParsedCommand command = ParsedCommand.fromInput(line);
            CommandHandler.handleCommand(command, supportedCommands);
        }
    }
}

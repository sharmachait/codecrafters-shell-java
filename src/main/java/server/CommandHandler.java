package server;

import Commands.Command;
import Parser.ParsedCommand;
import utils.CommandUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandHandler {
    public static void handleCommand(ParsedCommand command,
                                      Map<String, Command> supportedCommands) {
        if(supportedCommands.containsKey(command.command)){
            supportedCommands.get(command.command).execute(command);
        }else{
            List<String> commandLocations = CommandUtils.checkCommandInPaths(command.command);
            if(commandLocations.isEmpty()){
                System.out.println(command.command + ": command not found");
            }else{
                try{
                    int statusCode = runExecutable(command, commandLocations);
                    if(statusCode != 0){
                        System.out.println(command.command + ": command failed");
                    }
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
    private static int runExecutable(ParsedCommand command, List<String> commandLocations) throws IOException, InterruptedException {
        if(commandLocations.size() > 1){
            System.out.println("Multiple executables found");
        }
        String executablePath = commandLocations.getFirst();
        String[] arguments = command.args;
        File executable = new File(executablePath);
        if(!executable.exists() || !executable.canExecute()){
            System.out.println(executablePath + " is not executable");
            return 1;
        }
        String processName = executable.getName();
        ProcessBuilder processBuilder = new ProcessBuilder(processName);
        processBuilder.command().addAll(Arrays.asList(arguments));


        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process process = processBuilder.start();


        return process.waitFor();

    }
}

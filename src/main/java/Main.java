import Commands.*;
import Parser.ParsedCommand;
import utils.CommandUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static Map<String, Command> supportedCommands;
    public static void main(String[] args) throws Exception {
        String path = System.getenv("PATH");
        List<String> pathDirectories = Arrays.stream(path.split(":")).collect(Collectors.toList());
//        System.out.println(pathDirectories);
        supportedCommands = new HashMap<>();
        supportedCommands.put("exit", new ExitCommand());
        supportedCommands.put("echo", new EchoCommand());
        supportedCommands.put("pwd", new PWDCommand());
        supportedCommands.put("cd", new CDCommand());
        supportedCommands.put("type", new TypeCommand(supportedCommands, pathDirectories));

        while(true){
            ParsedCommand command = readCommand();
            handleCommand(command, pathDirectories);
        }
    }

    private static void handleCommand(ParsedCommand command, List<String> pathDirectories) {
        if(supportedCommands.containsKey(command.command)){
            supportedCommands.get(command.command).execute(command);
        }else{
            List<String> commandLocations = CommandUtils.checkCommandInPaths(command.command, pathDirectories);
            if(commandLocations.isEmpty()){
                System.out.println(command.command + ": command not found");
            }else{
                try{
                    runExecutable(command, commandLocations);
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
        String executablePath = commandLocations.get(0);
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


        int exitCode = process.waitFor();

        return exitCode;

    }

    public static ParsedCommand readCommand(){
        System.out.print("$ ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

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
        while(i<line.length()){
            //skip all whitespaces
            while(i < line.length() && line.charAt(i) == ' ') {
                i++;
            }
            if(i >= line.length()) break;


            char curr = line.charAt(i);
            if(curr == '\''){
                i++;
                StringBuilder sb = new StringBuilder();
                while(i < line.length() && line.charAt(i) != '\'') {
                    sb.append(line.charAt(i));
                    i++;
                }
                if(i < line.length()) {
                    i++;
                }
                arguments.add(sb.toString());
            }else{
                StringBuilder sb = new StringBuilder();
                while(i < line.length() && line.charAt(i) != ' ') {
                    sb.append(line.charAt(i));
                    i++;
                }
                arguments.add(sb.toString());
            }
        }
        parsedCommand.args = arguments.toArray(new String[arguments.size()]);
        return parsedCommand;
    }
}

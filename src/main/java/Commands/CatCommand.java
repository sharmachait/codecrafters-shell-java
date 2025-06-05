package Commands;

import Parser.ParsedCommand;
import utils.CommandUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CatCommand implements Command {
    @Override
    public void execute(ParsedCommand parsedCommand) {
        List<String> args = Arrays.stream(parsedCommand.args).collect(Collectors.toList());
        System.out.println(args);
        for (String path : parsedCommand.args) {
            try {
                File file = CommandUtils.resolvePath(path);
                if (!file.exists()) {
                    System.out.println("cat: " + path + ": No such file or directory");
                    continue;
                }

                if (file.isDirectory()) {
                    System.out.println("cat: " + path + ": Is a directory");
                    continue;
                }

                System.out.print(CommandUtils.readFileContent(file));
            } catch (Exception e) {
                System.out.println("cat: " + path + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void type() {
        System.out.println("cat is a shell builtin");
    }
}

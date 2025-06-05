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
        System.out.println();
    }

    @Override
    public void type() {
        List<String> paths = CommandUtils.getPaths();
        paths = CommandUtils.checkCommandInPaths("cat", paths);
        if(paths.isEmpty()) {
            System.out.println("cat: No such file or directory");
            return;
        }
        System.out.println("cat is "+paths.getFirst());
    }
}

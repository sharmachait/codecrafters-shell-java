package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CommandUtils {
    public static List<String> checkCommandInPaths(String command, List<String> paths) {
        List<String> commandLocations = new ArrayList<>();

        for (String dir : paths) {
            File lsFile = new File(dir, command); // Check for "ls" in this directory
            if (lsFile.exists() && lsFile.canExecute()) {
                commandLocations.add(lsFile.getAbsolutePath());
            }
        }

        return commandLocations;
    }
    public static File resolvePath(String path) throws IOException {
        File targetDir = new File(path);

        if(!targetDir.isAbsolute()) {
            // if starting with . then its relative
            targetDir = new File(System.getProperty("user.dir"), path);
        }

        targetDir = targetDir.getCanonicalFile();

        return targetDir;
    }

    public static String readFileContent(File file) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
        }
        return content.toString();
    }
    public static List<String> getPaths(){
        String path = System.getenv("PATH");
        return Arrays.stream(path.split(":")).collect(Collectors.toList());
    }
}

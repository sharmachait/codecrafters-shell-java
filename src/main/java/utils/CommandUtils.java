package utils;

import java.io.File;
import java.util.*;

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
}

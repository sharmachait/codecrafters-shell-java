package Commands;

import Parser.ParsedCommand;
import utils.CommandUtils;

import java.io.File;

public class CDCommand implements Command {
    @Override
    public void execute(ParsedCommand parsedCommand) {
        if(parsedCommand.args.length > 1){
            System.out.println("cd: too many arguments");
            return ;
        }else if(parsedCommand.args.length == 0){
            System.setProperty("user.dir", getHomeDirectory());
            return ;
        }
        String targetPath = parsedCommand.args[0];
        if(targetPath.equals("~") || targetPath.startsWith("~/")) {
            targetPath = targetPath.replace("~", getHomeDirectory());
        }

        try {
            // Normalize the path (resolve .. and . references)
            File targetDir = CommandUtils.resolvePath(targetPath);

            if(!targetDir.exists()) {
                System.out.println("cd: " + parsedCommand.args[0] + ": No such file or directory");
                return;
            }

            if(!targetDir.isDirectory()) {
                System.out.println("cd: " + parsedCommand.args[0] + ": Not a directory");
                return;
            }

            // Change directory
            System.setProperty("user.dir", targetDir.getAbsolutePath());

        } catch(Exception e) {
            System.out.println("cd: " + parsedCommand.args[0] + ": " + e.getMessage());
        }
    }

    @Override
    public void type() {
        System.out.println("cd is a shell builtin");
    }

    private String getHomeDirectory() {

        String homeFromEnv = System.getenv("HOME");
        if (homeFromEnv != null && !homeFromEnv.isEmpty()) {
            return homeFromEnv;
        }

        return System.getProperty("user.home");
    }
}

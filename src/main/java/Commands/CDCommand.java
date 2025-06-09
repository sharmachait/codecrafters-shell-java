package Commands;

import Parser.ParsedCommand;
import utils.CommandUtils;

import java.io.File;

public class CDCommand implements Command {
    @Override
    public void execute(ParsedCommand parsedCommand) {
        if(parsedCommand.args.size() > 1){
            System.out.println("cd: too many arguments");
            return ;
        }else if(parsedCommand.args.isEmpty()){
            System.setProperty("user.dir", getHomeDirectory());
            return ;
        }
        String targetPath = parsedCommand.args.getFirst();
        if(targetPath.equals("~") || targetPath.startsWith("~/")) {
            targetPath = targetPath.replace("~", getHomeDirectory());
        }

        try {
            // Normalize the path (resolve .. and . references)
            File targetDir = CommandUtils.resolvePath(targetPath);

            if(!targetDir.exists()) {
                System.out.println("cd: " + parsedCommand.args.getFirst() + ": No such file or directory");
                return;
            }

            if(!targetDir.isDirectory()) {
                System.out.println("cd: " + parsedCommand.args.getFirst() + ": Not a directory");
                return;
            }

            // Change directory
            System.setProperty("user.dir", targetDir.getAbsolutePath());

        } catch(Exception e) {
            System.out.println("cd: " + parsedCommand.args.getFirst() + ": " + e.getMessage());
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

import Commands.*;
import server.ShellServer;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Command> supportedCommands = new HashMap<>();
        supportedCommands.put("exit", new ExitCommand());
        supportedCommands.put("echo", new EchoCommand());
        supportedCommands.put("pwd", new PWDCommand());
        supportedCommands.put("cd", new CDCommand());
        supportedCommands.put("cat", new CatCommand());
        supportedCommands.put("type", new TypeCommand(supportedCommands));

        ShellServer.start(supportedCommands);
    }
}

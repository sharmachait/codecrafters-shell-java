package Commands;

import Parser.ParsedCommand;

public class ExitCommand implements Command {

    @Override
    public void execute(ParsedCommand parsedCommand) {
        StatusReport statusReport = checkArgs(parsedCommand.args);
        if (statusReport.success) {
            System.exit(statusReport.exitCode);
        }
    }

    @Override
    public void type() {
        System.out.println("exit is a shell builtin");
    }

    private StatusReport checkArgs(String[] args) {
        StatusReport statusReport = new StatusReport();
        statusReport.success=false;
        statusReport.exitCode=1;

        if(args.length == 0) {
            statusReport.exitCode=0;
            statusReport.success=true;
            return statusReport;
        } else if (args.length > 1) {
            System.out.println("-bash: exit: too many arguments");
            return statusReport;
        } else{
            statusReport.success=true;
            String statusCode = args[0];
            try{
                statusReport.exitCode = Integer.parseInt(statusCode);
            }catch (NumberFormatException e) {
                System.out.println("-bash: exit: " + statusCode+ ": numeric argument required");
            }
        }
        return statusReport;
    }

    static class StatusReport {
        public boolean success;
        public int exitCode;
    }
}

package Commands;

public interface Command {
    void execute();
    void execute(String[] args);
//    String execute(int statusCode);
}

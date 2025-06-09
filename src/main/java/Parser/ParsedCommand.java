package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ParsedCommand {
    public static final Set<Character> quoteSymbols = Set.of('\'', '"', '`');
    public String command;
    public List<String> args;
    public ParsedCommand() {
        args = new ArrayList<>();
    }
    public ParsedCommand(String cmdName) {
        command = cmdName;
        args = new ArrayList<>();
    }
    public static ParsedCommand fromInput(String input) {
        if (input.isEmpty()) {
            return null;
        }
        ParsedCommand cmd = null;
        StringBuilder sb = new StringBuilder();
        int i;
        for(i=0; i<input.length(); i++) {
            char c = input.charAt(i);
            if(c == ' '){
                if(cmd == null && !sb.isEmpty()){
                    cmd = new ParsedCommand();
                    sb.delete(0, sb.length());
                }
            } else if(cmd != null){
                break;
            } else {
                sb.append(c);
            }
        }

        if(!sb.isEmpty()){
            cmd = new ParsedCommand(sb.toString());
            sb.delete(0, sb.length());
        } else if (cmd == null) {
            return null;
        }

        char quoteChar = '\0';
        boolean isSpace = false;
        for (; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == ' ') {
                if (quoteChar != '\0') {
                    sb.append(c);
                } else if (!isSpace) {
                    isSpace = true;
                    if (!sb.isEmpty()) {
                        cmd.args.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                }
            }else {
                if (isSpace) {
                    isSpace = false;
                    cmd.args.add(" ");
                }
                sb.append(c);
                if (quoteSymbols.contains(c)) {
                    if (quoteChar == c) {
                        quoteChar = '\0';
                        cmd.args.add(sb.toString());
                        sb.delete(0, sb.length());
                    } else if (quoteChar == '\0'){
                        quoteChar = c;
                        if (sb.length() > 1) {
                            cmd.args.add(sb.substring(0, sb.length() - 1));
                            sb.delete(0, sb.length() - 1);
                        }
                    }
                }
            }
        }
        return cmd;
    }
}

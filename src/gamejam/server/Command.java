package gamejam.server;

import lombok.Getter;
import lombok.Setter;

/**
 * This class is immutable!
 * @author Rick Lubbers
 *
 */
public class Command {
	
	public class Cmd {
		public static final String CONNECT = "CONNECT";
		public static final String UPDATE = "UPDATE";
		public static final String CHAT = "CHAT";
	}
	
	public static final String[] CMDS = { Cmd.CONNECT, Cmd.UPDATE, Cmd.CHAT };
	private String cmd;
	private String[] args;
	
	public Command(String cmd) {
		this.cmd = cmd;
	}
	
	public Command(String cmd, String[] args) {
		this(cmd);
		this.args = args;
	}
	
	public String getCmd() {
		return cmd;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public Command addArgument(String arg) {
		String[] newargs = new String[args.length + 1];
		System.arraycopy(args, 0, newargs, 0, args.length);
		newargs[newargs.length - 1] = arg;
		return new Command(cmd, newargs);
	}
	
	public static Command parseCommand(String input) {
		String[] parts = input.split("\\s+");
		boolean valid = false;
		for (int i = 0; i < CMDS.length; i++) {
			if (CMDS[i] == parts[0]) {
				valid = true;
				break;
			}
		}
		if (valid) {
			return new Command(parts[0]);
		} else {
			return null;
		}
	}
	
	public String toString() {
		String output = cmd;
		for (int i = 0; i < args.length; i++) {
			output += " " + args[i];
		}
		return output;
	}
	
}

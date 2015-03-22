package gamejam.server;

import java.util.Arrays;

public class Command {

	public enum CommandType {
		CONNECT, OBJECT, CHAT, MOVE, QUIT
	}

	private final CommandType command;
	private String[] arguments;

	public Command(CommandType command) {
		this(command, null);
	}

	public Command(CommandType command, String[] arguments) {
		this.command = command;
		this.arguments = arguments;
	}

	public CommandType getCommandType() {
		return command;
	}

	public String[] getArguments() {
		return arguments;
	}

	public Command addArgument(String argument) {
        String[] temp = arguments == null ? new String[0] : arguments;
		arguments = new String[temp.length + 1];
		System.arraycopy(temp, 0, arguments, 0, temp.length);
		arguments[arguments.length - 1] = argument;
        return this;
	}

	public String toString() {
		String result = command.name();
		if (arguments != null) {
			for (String argument : arguments) {
				result += String.format(" %s", argument);
			}
		}
		return result;
	}

	public static Command parseCommand(String line) throws IllegalArgumentException {
		String[] words = line.split("\\s");

        CommandType type = null;
        for (CommandType c : CommandType.values()) {
            if (c.name().equals(words[0])) {
                type = c;
            }
        }

        if (type != null) {
            Command result;
            if (words.length > 1) {
                result = new Command(type, Arrays.copyOfRange(words, 1, words.length));
            } else {
                result = new Command(type);
            }
            return result;
        }

        throw new IllegalArgumentException();
	}
}

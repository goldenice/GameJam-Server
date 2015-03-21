package gamejam.server;

public class IncomingCommandHandler {

	private Client client;
	
	public IncomingCommandHandler(Client client) {
		this.client = client;
	}
	
	public Command handle(Command input) {
		if (input.getCmd().equals(Command.Cmd.CONNECT)) {
			// TODO: implement connecting sequence
		} else {
			// Unrecognized command, do absolutely nothing
		}
		return null;
	}
	
}

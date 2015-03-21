package gamejam.server;

import gamejam.server.objects.nonphysical.Client;

public class IncomingCommandHandler {

	private Client client;
	
	public IncomingCommandHandler(Client client) {
		this.client = client;
	}
	
	public Command handle(Command input) {
		// CONNECT superice
		if (input.getCmd().equals(Command.Cmd.CONNECT)) {
			String[] args = input.getArgs();
			if (args[0] != null && args[0].equals("")) {
				client.setUsername(args[0]);
				client.sendAllObjects();
			}
		}
		return null;
	}
	
}

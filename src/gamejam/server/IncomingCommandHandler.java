package gamejam.server;

import gamejam.server.objects.nonphysical.Client;

public class IncomingCommandHandler {

	private Client client;
	
	public IncomingCommandHandler(Client client) {
		this.client = client;
	}
	
	public Command handle(Command input) throws IllegalArgumentException {
		switch (input.getCommandType()) {
			case CONNECT:
				String[] arguments = input.getArguments();
				if (arguments != null && arguments.length == 1) {
					String username = arguments[0];
					client.setUsername(username);
					client.sendAllObjects();
				} else {
					throw new IllegalArgumentException();
				}
				break;
		}
		return null;
	}
	
}

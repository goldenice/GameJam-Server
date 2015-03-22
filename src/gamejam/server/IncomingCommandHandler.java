package gamejam.server;

import gamejam.server.objects.PhysicalEntity;
import gamejam.server.objects.Ship;
import gamejam.server.objects.World;
import gamejam.server.objects.nonphysical.Client;

import java.io.IOException;

public class IncomingCommandHandler {

	private Client client;
	
	public IncomingCommandHandler(Client client) {
		this.client = client;
	}
	
	public Command handle(Command input) throws IllegalArgumentException {
		switch (input.getCommandType()) {
			case CONNECT: {
				String[] arguments = input.getArguments();
				if (arguments != null && arguments.length == 1) {
					String username = arguments[0];
					client.setUsername(username);
                    client.sendAllEntities();
                    Ship ship = new Ship(0,0,0,0,0,0, username);
                    int id = ship.getObjectId();
                    PhysicalEntity physical = ship;
                    Command command = new Command(Command.CommandType.OBJECT);
                    command = command
                            .addArgument(Integer.toString(physical.getObjectId()))
                            .addArgument(physical.getType())
                            .addArgument(Float.toString(physical.getX()))
                            .addArgument(Float.toString(physical.getY()))
                            .addArgument(Float.toString(physical.getZ()))
                            .addArgument(Float.toString(physical.getPitch()))
                            .addArgument(Float.toString(physical.getYaw()))
                            .addArgument(Float.toString(physical.getRoll()));
                    try {
                        client.send(new Command(Command.CommandType.CONTROL, new String[]{Integer.toString(id)}));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    client.getServer().broadcast(command);
                    client.setIdentifier(id);
				} else {
					throw new IllegalArgumentException();
				}}
				break;
            case MOVE: {
                    String[] arguments = input.getArguments();
                    if (arguments != null && arguments.length == 6){
                        PhysicalEntity physical = ((PhysicalEntity) World.getInstance().getEntityById(client.getIdentifier()));
                        physical.updatePosition(Float.parseFloat(arguments[0]), Float.parseFloat(arguments[1]), Float.parseFloat(arguments[2]));
                        ((PhysicalEntity)World.getInstance().getEntityById(client.getIdentifier())).updateRotation(Float.parseFloat(arguments[3]), Float.parseFloat(arguments[4]), Float.parseFloat(arguments[5]));

                        Command command = new Command(Command.CommandType.UPDATE);
                        command = command
                                .addArgument(Integer.toString(physical.getObjectId()))
                                .addArgument(physical.getType())
                                .addArgument(Float.toString(physical.getX()))
                                .addArgument(Float.toString(physical.getY()))
                                .addArgument(Float.toString(physical.getZ()))
                                .addArgument(Float.toString(physical.getPitch()))
                                .addArgument(Float.toString(physical.getYaw()))
                                .addArgument(Float.toString(physical.getRoll()));

                        client.getServer().broadcast(command);
                    } else {
                throw new IllegalArgumentException();
                }
                break;

                }
            case QUIT: {
                client.close();
            }
                break;
		}
		return null;
	}
	
}

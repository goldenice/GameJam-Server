package gamejam.server.objects.nonphysical;

import gamejam.server.Command;
import gamejam.server.IncomingCommandHandler;
import gamejam.server.Server;
import gamejam.server.objects.Entity;
import gamejam.server.objects.PhysicalEntity;
import gamejam.server.objects.World;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;

public class Client extends Entity implements Runnable {
	
	private Socket socket;
    private Server server;
	private BufferedReader in;
	private BufferedWriter out;
	private IncomingCommandHandler ich;
	
	private String username;
	private int identifier;
	private SpiritualEntity user;

	private boolean running;
	
	public Client(Socket socket, Server server) {
		super("Client");
		this.socket = socket;
        this.server = server;
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.ich = new IncomingCommandHandler(this);
		} catch (IOException e) {
			System.err.println("Something went wrong while opening a client socket");
			e.printStackTrace();
		}
	}

    public void setIdentifier(int identifier){
        this.identifier = identifier;
    }

    public int getIdentifier(){
        return identifier;
    }

    public Server getServer(){
        return this.server;
    }

	@Override
	public void run() {
		running = true;
		try {
			while (running) {
				String line = in.readLine();
				if (line != null) {
					try {
						System.out.println(line);
						Command command = Command.parseCommand(line);
						Command out = ich.handle(command);
						if (out != null) send(out);
					} catch (IllegalArgumentException e) {
						System.err.println("Received illegal input from client thread.");
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Something went wrong while reading from socket");
			e.printStackTrace();
			this.close();
		}
	}
	
	public void send(Command input) throws IOException {
		out.write(input.toString());
		out.newLine();
		out.flush();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void sendAllEntities() {
		Map<Integer, Entity> map = World.getInstance().getEntityMap();
		for (Integer key : map.keySet()) {
			Entity entity = map.get(key);
			if (entity instanceof PhysicalEntity) {
				PhysicalEntity physical = (PhysicalEntity) entity;
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
					send(command);
				} catch (IOException e) {
					System.err.println("Something went wrong while sending all objects to the client");
					e.printStackTrace();
				}
			}
		}
	}
	
	public void close() {
		try {
			in.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("Errors closing the connection with a client");
			e.printStackTrace();
		}
	}
	
}

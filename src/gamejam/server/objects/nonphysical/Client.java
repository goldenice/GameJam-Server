package gamejam.server.objects.nonphysical;

import gamejam.server.Command;
import gamejam.server.IncomingCommandHandler;
import gamejam.server.objects.GameObject;
import gamejam.server.objects.PhysicalObject;
import gamejam.server.objects.World;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;

public class Client implements Runnable {
	
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	private IncomingCommandHandler ich;
	
	private String username;
	private int obj_id;
	private NonPhysical user;
	
	public Client(Socket sock) {
		this.sock = sock;
		try {
			this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));	
			this.ich = new IncomingCommandHandler(this);
		} catch (IOException e) {
			System.err.println("Something went wrong while opening a client socket");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				String input = null;
				while (true) {
					if ((input = in.readLine()) != null) {
						System.out.println("INPUT: " + input);
						Command cmd = parseInput(input);
						if (cmd != null) {
							Command out = null;
							if ((out = ich.handle(cmd)) != null) {
								send(out);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Something went wrong while reading from socket");
			e.printStackTrace();
			this.close();
		}
	}
	
	public Command parseInput(String input) {
		return Command.parseCommand(input);
	}
	
	public void send(Command input) throws IOException {
		out.write(input.toString());
		out.newLine();
		out.flush();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void sendAllObjects() {
		Map<Integer, GameObject> map = World.getInstance().getMap();
		for (Integer key : map.keySet()) {
			GameObject obj = map.get(key);
			if (obj instanceof PhysicalObject) {
				PhysicalObject phys = (PhysicalObject) obj;
				Command cmd = new Command(Command.Cmd.OBJECT);
				cmd = cmd
						.addArgument(new Integer(phys.getObjectId()).toString())
						.addArgument(phys.getType())
						.addArgument(new Float(phys.getX()).toString())
						.addArgument(new Float(phys.getY()).toString())
						.addArgument(new Float(phys.getZ()).toString());
				try {
					send(cmd);
				} catch (Exception e) {
					System.err.println("Something went wrong while sending all objects to the client");
					e.printStackTrace();
				}
			}
		}
	}
	
	public void close() {
		try {
			in.close();
			sock.close();
		} catch (IOException e) {
			System.err.println("Errors closing the connection with a client");
			e.printStackTrace();
		}
	}
	
}

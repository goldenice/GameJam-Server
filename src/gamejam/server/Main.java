package gamejam.server;

import gamejam.server.objects.nonphysical.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import lombok.Getter;

public class Main {

	/******************* MAGIC STATIC LINE *******************/
	
	private static Main main;
	
	public static void main(String[] args) {
		ServerSocket server;
		try {
			server = new ServerSocket(6969);
			Main.main = new Main(server);
			Main.main.listen();
		} catch (IOException e) {
			System.err.println("Could not open socket");
		} catch (Exception e) {
			System.err.println("Unhandled exception!");
			e.printStackTrace();
		}
	}
	
	public static Main getServer() {
		return Main.main;
	}
	

	/******************* END MAGIC STATIC LINE ***************/
	
	
	public Main(ServerSocket server) {
		this.server = server;
	}
	
	private ServerSocket server;
	private ArrayList<Client> clients = new ArrayList<Client>();
	private boolean execute = true;
	
	public void listen() {
		try {
			while (execute) {
				Socket sock = server.accept();
				Client newclient = new Client(sock);
				new Thread(newclient).start();
				clients.add(newclient);
			}
		} catch (IOException e) {
			System.err.println("Something went wrong while accepting clients");
			e.printStackTrace();
		}
	}
	
	public void broadcast(Command cmd) {
		for (Client c : clients) {
			try {
				c.send(cmd);
			} catch (IOException e) {
				System.err.println("Something went wrong while broadcasting to a client");
				e.printStackTrace();
			}
		}
	}
	
}

package gamejam.server;

import gamejam.server.objects.nonphysical.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static final int LISTEN_PORT = 6969;

	private ServerSocket serverSocket;
	private ArrayList<Client> clients = new ArrayList<Client>();
	private boolean running = true;

	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void start() {
		try {
			while (running) {
				Socket sock = serverSocket.accept();
				Client client = new Client(sock);
				new Thread(client).start();
				clients.add(client);
			}
		} catch (IOException e) {
			System.err.println("Something went wrong while accepting clients");
			e.printStackTrace();
		}
	}

	public void stop() {
		if (running) running = false;
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

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(LISTEN_PORT);
			Server server = new Server(serverSocket);
			server.start();
		} catch (IOException e) {
			System.err.println("Could not open socket");
		} catch (Exception e) {
			System.err.println("Unhandled exception!");
			e.printStackTrace();
		}
	}
}

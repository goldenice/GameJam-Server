package gamejam.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import lombok.Getter;

public class Main {
	
	private ServerSocket server;
	private ArrayList<Client> clients;
	private boolean execute = true;
	
	public static void main(String[] args) {
		ServerSocket server;
		try {
			server = new ServerSocket(6969);
			new Main(server).listen();
		} catch (IOException e) {
			System.err.println("Could not open socket");
		} catch (Exception e) {
			System.err.println("Unhandled exception!");
			e.printStackTrace();
		}
	}
	
	public Main(ServerSocket server) {
		this.server = server;
	}
	
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
	
}

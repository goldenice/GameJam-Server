package gamejam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client implements Runnable {
	
	private Socket sock;
	private BufferedReader in;
	
	public Client(Socket sock) {
		this.sock = sock;
		try {
			this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			System.err.println("Something went wrong while opening a client socket");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			String input = null;
			while ((input = in.readLine()) == null) {
				
			}
		}
	}
	
	public void close() {
		// TODO: implement
	}
	
}

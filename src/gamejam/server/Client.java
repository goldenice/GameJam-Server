package gamejam.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client implements Runnable {
	
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	private IncomingCommandHandler ich;
	
	public Client(Socket sock) {
		this.sock = sock;
		try {
			this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));	
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
				while ((input = in.readLine()) == null) {
					Command cmd = parseInput(input);
					Command out = null;
					if ((out = ich.handle(cmd)) != null) {
						send(out);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Something went wrong while reading from socket");
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

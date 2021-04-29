package Main;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private static final String FILE = "set.txt";
	private final int port;
	private final String host;
	private final String URL = "hello.html";

	public Client(int port, String host) {
		this.port = port;
		this.host = host;
	}
	
	public String makeRequest() {
		String request = "GET /";
		request += URL;
		request += " HTTP/1.1\r\nHost: ";
		request += host;
		request += "\r\nConnection: close\r\n\r\n\"";
		return request;
	}
	
	void start() {
		try (Socket socket = new Socket(host, port);
				PrintStream writer = new PrintStream(socket.getOutputStream());
				Scanner reader = new Scanner(socket.getInputStream());) {
			System.out.println("Connected to server!");
			String request = makeRequest();
			writer.println(request);
			writer.flush();
			System.out.println("Request: " + request);
			String response = reader.nextLine();
			System.out.println("Response: " + response);
			while (reader.hasNextLine()) {
				System.out.println(reader.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(new File(FILE));
		int port = scanner.nextInt();
		String host = scanner.nextLine();
		host = scanner.nextLine();
		new Client(port, host).start();
	}
}
package com.sgs.mcma.webservice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sgs.mcma.webservice.handler.PlayerJoinedHandler;
import com.sgs.mcma.webservice.handler.PlayerLeftHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

	private static int SERVER_PORT_NUMBER;
	private static final int MAX_WAITING_CONNECTIONS = 10;

	private static String serveraddress;
	
	public Server(String hostaddress, int port) {
		serveraddress="localhost";
		SERVER_PORT_NUMBER = port;
	}
	
	public static String getServerAddress() {
		return serveraddress;
	}

	public static void setAddress(String address) {
		serveraddress = "http://" + address + ":" + SERVER_PORT_NUMBER
				+ "/GetFile/Records/";
	}

	private static Logger logger;

	static {
		try {
			initLog();
		} catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}

	private static void initLog() throws IOException {

		Level logLevel = Level.FINE;

		logger = Logger.getLogger("MCMA");
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);

		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	private HttpServer server;

	public void run() {

		logger.info("Initializing HTTP Server on port:" + SERVER_PORT_NUMBER);

		try {
			server = HttpServer.create(
					new InetSocketAddress(SERVER_PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}

		server.setExecutor(null); // use the default executor

		server.createContext("/PlayerJoined", playerJoinedHandler);
		server.createContext("/PlayerLeft", playerLeftHandler);

		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler playerJoinedHandler = new PlayerJoinedHandler();
	private HttpHandler playerLeftHandler = new PlayerLeftHandler();

	public static void main(String[] args) {
		if (args.length == 1) {
			SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
		}
		new Server("localhost", 39640).run();
	}
}

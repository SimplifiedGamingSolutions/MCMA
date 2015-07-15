package com.sgs.mcma.webservice.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.rmi.ServerException;
import java.util.logging.Logger;

import com.sgs.mcma.webservice.Server;
import com.sgs.mcma.webservice.ServerFacade;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PlayerLeftHandler implements HttpHandler
{

	private Logger logger = Logger.getLogger("MCMA");

	public void handle(HttpExchange exchange) throws IOException
	{
		logger.entering("PlayerLeftHandler", "handle");
		try
		{
			Server.setAddress(exchange.getLocalAddress().getHostString());
			InputStream requestBody = exchange.getRequestBody();
			ObjectInput in = new ObjectInputStream(requestBody);
			String request = (String) in.readObject();
			in.close();
			requestBody.close();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			boolean result = ServerFacade.removePlayer(request);
			OutputStream responseBody = exchange.getResponseBody();
			ObjectOutput out = new ObjectOutputStream(responseBody);
			out.writeObject(result);
			out.close();
			responseBody.close();

		} catch (ClassNotFoundException e)
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
			logger.severe("Error in PlayerLeftHandler.handle(): " + e.getMessage());
			e.printStackTrace();
		} catch (ServerException e)
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
			logger.severe("Error in PlayerLeftHandler.handle(): " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (exchange != null)
			{
				exchange.close();
			}
			logger.exiting("PlayerLeftHandler", "handle");
		}
	}

}

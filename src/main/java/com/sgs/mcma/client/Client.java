package com.sgs.mcma.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Client {

	String serveraddress;
	int port;

	public Client(String serveraddress, int port) {
		this.serveraddress = serveraddress;
		this.port = port;
	}

	public boolean PlayerJoined(String player)
			throws ClientException, IOException {
		return (Boolean) doPost("/PlayerJoined", new PlayerJoined_Params(player));
	}

	public boolean PlayerLeft(String player)
			throws ClientException, IOException {
		return (Boolean) doPost("/PlayerLeft", new PlayerLeft_Params(player));
	}

	private Object doPost(String urlPath, Object postData)
			throws ClientException {
		try {
			URL url = new URL("http", serveraddress, port, urlPath);
			HttpURLConnection connection = (HttpURLConnection) url .openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.connect();
			OutputStream requestBody = connection.getOutputStream();
			// Write request body to OutputStream ...
			ObjectOutput out = new ObjectOutputStream(requestBody);
			out.writeObject(postData);
			requestBody.close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream responseBody = connection.getInputStream();
				// Read response body from InputStream ...
				ObjectInput in = null;
				in = new ObjectInputStream(responseBody);
				Object returnobj = in.readObject();
				in.close();
				responseBody.close();
				connection.disconnect();
				return returnobj;
			} else {
				connection.disconnect();
				throw new ClientException();
			}
		} catch (Exception e) {
			throw new ClientException(e);
		}
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable(){

			public void run() {
				boolean result;
				try {
					result = new Client("localhost", 39640).PlayerLeft("testplayer");
					//System.out.println(result);
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
}

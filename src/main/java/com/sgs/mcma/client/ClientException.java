package com.sgs.mcma.client;

@SuppressWarnings("serial")
public class ClientException extends Exception {

	public ClientException(Exception e) {
		super(e);
	}

	public ClientException() {
	}

	public ClientException(String string) {
		super("string");
	}

}

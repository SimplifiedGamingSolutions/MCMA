package com.sgs.mcma.shared.communication;

public class ClientException extends Exception {

	public ClientException(Exception e) {
		super(e);
	}

	public ClientException() {
		// TODO Auto-generated constructor stub
	}

	public ClientException(String string) {
		super("string");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4230710769569754830L;

}

package com.sgs.mcma.client;

import java.io.Serializable;

public class PlayerLeft_Params implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2015144526765569124L;
	private String name;
	
	public PlayerLeft_Params(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

}

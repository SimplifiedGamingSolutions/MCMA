package com.sgs.mcma.client;

import java.io.Serializable;

public class PlayerJoined_Params implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6702464906182395988L;
	private String name;
	
	public PlayerJoined_Params(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

}

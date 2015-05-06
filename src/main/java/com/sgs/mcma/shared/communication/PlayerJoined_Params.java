package com.sgs.mcma.shared.communication;

import java.io.Serializable;

public class PlayerJoined_Params implements Serializable{
	private String name;
	
	public PlayerJoined_Params(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

}

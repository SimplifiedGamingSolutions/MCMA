package com.sgs.mcma.shared.communication;

import java.io.Serializable;

public class PlayerLeft_Params implements Serializable{
	private String name;
	
	public PlayerLeft_Params(String name) {
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

}

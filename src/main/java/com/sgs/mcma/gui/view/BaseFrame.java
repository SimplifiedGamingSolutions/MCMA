package com.sgs.mcma.gui.view;

import java.awt.Dimension;

import javax.swing.JFrame;

public class BaseFrame extends JFrame {
	
	public BaseFrame(String title){
		Initialize(title);
	}

	private void Initialize(String title) {
		this.setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(1024,700));
		populateFrame();
	}

	private void populateFrame() {
		// TODO Auto-generated method stub
		
	}

}

package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

public class TestFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4756577077114089341L;

	public TestFrame(Component component){
        JFrame frame = new JFrame ("cmd");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 700);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(component, BorderLayout.CENTER);
        frame.setVisible (true);
	}
}

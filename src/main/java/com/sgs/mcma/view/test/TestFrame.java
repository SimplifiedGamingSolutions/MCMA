package com.sgs.mcma.view.test;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TestFrame extends JFrame
{

	public TestFrame(Component component)
	{
		JFrame frame = new JFrame("cmd");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 700);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(component, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}

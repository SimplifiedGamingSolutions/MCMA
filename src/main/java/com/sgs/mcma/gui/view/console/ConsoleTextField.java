package com.sgs.mcma.gui.view.console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.JTextField;

public class ConsoleTextField extends JTextField {
	public ConsoleTextField(final BufferedWriter output){
		super();
		this.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
		        try {
		        	JTextField text = (JTextField) e.getSource();
					output.write(text.getText()+'\n');
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}

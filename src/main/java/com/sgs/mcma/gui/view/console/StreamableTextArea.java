package com.sgs.mcma.gui.view.console;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;


public class StreamableTextArea extends JTextArea {
	private JTextArea instance = this;
	
	public StreamableTextArea(int i, int j) {
		super(i,j);
	}

	public OutputStream getOutputStream(){
		return new OutputStream(){
			@Override
			public void write(int b) throws IOException {
		        instance.append( String.valueOf( ( char )b ) );
			}
		};
	}
}

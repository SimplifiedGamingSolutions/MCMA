package com.sgs.mcma.gui.view.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sgs.mcma.gui.view.TestFrame;

public class ControllableProcess {
	private ProcessBuilder pb;
	private Process p;
	private JTextPaneInputStreamPrinter inputStreamPrinter;
	private Thread inputListener;
	private JTextPaneInputStreamPrinter errorStreamPrinter;
	private Thread errorListener;
	private BufferedWriter output;
	private StyledDocument doc;
	private SimpleAttributeSet simpleText;
	private SimpleAttributeSet errorText;

	public ControllableProcess(String processPath, StyledDocument document, SimpleAttributeSet simpleText, SimpleAttributeSet errorText) {
		doc = document;
		this.simpleText = simpleText;
		this.errorText = errorText;
		pb = new ProcessBuilder(processPath);
	}
	
	public boolean start(){
		try{
			if(p == null){
				p = pb.start();
				initializeIO();
				return true;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	private void initializeIO() {
        output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        inputStreamPrinter = new JTextPaneInputStreamPrinter(p.getInputStream(), false);
        inputListener = new Thread(inputStreamPrinter);
        errorStreamPrinter = new JTextPaneInputStreamPrinter(p.getErrorStream(),true);
        errorListener = new Thread(errorStreamPrinter);
        inputListener.start();
        errorListener.start();
	}

	public boolean stop(){
		try{
			p.waitFor();
			p.destroy();
			p = null;
			inputStreamPrinter.pause();
			inputStreamPrinter = null;
			errorStreamPrinter.pause();
			errorStreamPrinter = null;
			inputListener.join();
			errorListener.join();
			inputListener = null;
			errorListener = null;
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void sendCommand(String text){
		try {
			output.write(text+'\n');
	    	output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendToJTextPane(String text, SimpleAttributeSet att){
		if(p.isAlive()){
			try {
				doc.insertString(doc.getLength(), text, att);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class JTextPaneInputStreamPrinter implements Runnable{
		private boolean running = true;
		private boolean isErrorStream;
		private InputStream stream;
    	byte[] inBuffer = new byte[1024];
    	
    	public JTextPaneInputStreamPrinter(InputStream stream, boolean isErrorStream){
    		this.stream = stream;
    		this.isErrorStream = isErrorStream;
    	}
    	
    	public void run(){
    		if(isErrorStream){
    			runStreamPrinter(errorText);
    		}
    		else{
    			runStreamPrinter(simpleText);
    		}
    	}
		public void runStreamPrinter(SimpleAttributeSet textStyle) {
			while(running){
		        try {
			    	int length = stream.read(inBuffer);
		        	if(length > -1) {
	                    appendToJTextPane(new String(inBuffer, 0, length), textStyle);
		        	}
				} 
		        catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		public void pause(){
			running = false;
		}
		public void resume(){
			running = true;
			run();
		}
    }
	public static void main(String[] args) {
    	SimpleAttributeSet consoleTextAttributeSet = new SimpleAttributeSet();
    	StyleConstants.setForeground(consoleTextAttributeSet, Color.LIGHT_GRAY);
    	StyleConstants.setBackground(consoleTextAttributeSet, Color.BLACK);
    	SimpleAttributeSet errorTextAttributeSet = new SimpleAttributeSet();
    	StyleConstants.setForeground(errorTextAttributeSet, Color.RED);
    	StyleConstants.setBackground(errorTextAttributeSet, Color.BLACK);
    	StyleConstants.setBold(errorTextAttributeSet, true);
    	JTextPane testpane = new JTextPane();
		final ControllableProcess server = new ControllableProcess("cmd.exe", testpane.getStyledDocument(), consoleTextAttributeSet, errorTextAttributeSet);
    	JPanel panel = new JPanel(new BorderLayout());
    	JButton startbutton = new JButton("start");
    	startbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				server.start();
			}
		});
    	JButton stopbutton = new JButton("stop");
    	stopbutton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				server.sendCommand("exit");
				server.stop();
			}
		});
    	panel.add(testpane, BorderLayout.CENTER);
    	panel.add(startbutton, BorderLayout.NORTH);
    	panel.add(stopbutton, BorderLayout.SOUTH);
    	TestFrame frame = new TestFrame(panel);
		server.start();
	}
}

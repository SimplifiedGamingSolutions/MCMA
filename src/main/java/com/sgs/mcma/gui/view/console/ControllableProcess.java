package com.sgs.mcma.gui.view.console;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

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
}

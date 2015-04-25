package com.sgs.mcma.gui.view.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sgs.mcma.gui.view.TestFrame;

public class ConsolePane extends JPanel{
	
    private JTextPane consoleTextPane;
    private StyledDocument doc;
    private SimpleAttributeSet consoleTextAttributeSet;
    private SimpleAttributeSet errorTextAttributeSet;
    private BufferedWriter output;
    private Process p;
    private Thread inputListener;
    private Thread errorListener;
    
    public ConsolePane(){
    	populateTextPane();
		populateConsolePane();
    }
    
    private void populateTextPane() {
    	consoleTextPane = new JTextPane();
    	DefaultCaret caret = (DefaultCaret)consoleTextPane.getCaret();
    	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    	consoleTextPane.setBackground(Color.BLACK);
    	consoleTextPane.setEditable(false);
    	doc = consoleTextPane.getStyledDocument();
	}

	private void populateConsolePane() {
		setLayout (new BorderLayout ());
		JScrollPane pane = new JScrollPane (consoleTextPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(pane, BorderLayout.CENTER);
        ConsoleCommandTextField field = new ConsoleCommandTextField();
        add(field,BorderLayout.SOUTH);
	}
	
	
	public Process CreateProcess(String command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.
        try {
			p = pb.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        inputListener = new Thread(new Runnable(){
			public void run() {
		        try {
			    	consoleTextAttributeSet = new SimpleAttributeSet();
			    	StyleConstants.setForeground(consoleTextAttributeSet, Color.LIGHT_GRAY);
			    	StyleConstants.setBackground(consoleTextAttributeSet, Color.BLACK);
			    	StyleConstants.setBold(consoleTextAttributeSet, true);
			    	byte[] inBuffer = new byte[1024];
		        	for (int i = 0; i > -1; i = p.getInputStream().read(inBuffer)) {
		        		String input = new String(inBuffer, 0, i);
                        appendTextToConsole(input);
		        	}
					p.waitFor();
				} 
		        catch (Exception e) {
					e.printStackTrace();
				}
			}
        });
        errorListener = new Thread(new Runnable(){
			public void run() {
		        try {
			    	errorTextAttributeSet = new SimpleAttributeSet();
			    	StyleConstants.setForeground(errorTextAttributeSet, Color.RED);
			    	StyleConstants.setBackground(errorTextAttributeSet, Color.BLACK);
			    	StyleConstants.setBold(errorTextAttributeSet, true);
			    	byte[] errorBuffer = new byte[1024];
		        	for (int i = 0; i > -1; i = p.getErrorStream().read(errorBuffer)) {
		        		String error = new String(errorBuffer, 0, i);
                        appendErrorToConsole('\n'+error);
		        	}
					p.waitFor();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        });
        errorListener.start();
        inputListener.start();
        return p;
    }
	
	private void sendCommand(String text){
		try {
			output.write(text+'\n');
	    	output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void appendTextToConsole(String text){
		if(p.isAlive()){
			try {
				doc.insertString(doc.getLength(), text, consoleTextAttributeSet);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void appendErrorToConsole(String text){
		try {
			doc.insertString(doc.getLength(), text, errorTextAttributeSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	private void clearConsole(){
		consoleTextPane.setText("");
	}
	class ConsoleCommandTextField extends JTextField {
    	public ConsoleCommandTextField(){
    		super();
    		this.addActionListener(new ActionListener() {
    			
    			public void actionPerformed(ActionEvent e) {
    				if(p != null && p.isAlive()){
    		        	if(getText().equals("clear")){
    		        		clearConsole();
    		        	}
    		        	else if(getText().equals("exit")){
    		        		clearConsole();
    						appendErrorToConsole("No running server");
    		        	}
    		        	else{
	    		        	sendCommand(getText());
    		        	}
    		        	clearCommand();
    				}
    				else{
    					consoleTextPane.setText("");
						appendErrorToConsole("No running server");
    		        	clearCommand();
    				}
    			}
    		});
    	}
    	private void clearCommand(){
    		setText("");
    	}
    }
	
	public static void main(String[] args) throws Exception {
		ConsolePane test = new ConsolePane();
		new TestFrame(test);
    	test.CreateProcess("cmd.exe");
    }

	public boolean stopProcess() {
		if(p.isAlive()){
			sendCommand("exit");
			clearConsole();
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}
		else{
			return false;
		}
	}
}


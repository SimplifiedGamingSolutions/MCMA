package com.sgs.mcma.gui.view.console;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sgs.mcma.gui.view.TestFrame;

public class ConsolePane extends JPanel{
	
    private JTextPane consoleTextPane;
    private StyledDocument doc;
    private SimpleAttributeSet consoleTextAttributeSet;
    private SimpleAttributeSet errorTextAttributeSet;
    
    private BufferedReader input;
    private BufferedWriter output;
    private BufferedReader error;
    private Process p;
    
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
	
	
	private void CreateProcess(String command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        p = pb.start();
        output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        new Thread(new Runnable(){
			public void run() {
		    	consoleTextAttributeSet = new SimpleAttributeSet();
		    	StyleConstants.setForeground(consoleTextAttributeSet, Color.LIGHT_GRAY);
		    	StyleConstants.setBackground(consoleTextAttributeSet, Color.BLACK);
		    	StyleConstants.setBold(consoleTextAttributeSet, true);
		        String line="";
		        input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        try {
					while ((line = input.readLine()) != null) {
						appendTextToConsole(line);
					}
				} 
		        catch (Exception e) {
					e.printStackTrace();
				}
			}
        }).start();
        new Thread(new Runnable(){
			public void run() {
		    	errorTextAttributeSet = new SimpleAttributeSet();
		    	StyleConstants.setForeground(errorTextAttributeSet, Color.RED);
		    	StyleConstants.setBackground(errorTextAttributeSet, Color.BLACK);
		    	StyleConstants.setBold(errorTextAttributeSet, true);
		        String line="";
		        error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		        try {
					while ((line = error.readLine()) != null) {
						appendErrorToConsole(line);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        }).start();
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
		try {
			doc.insertString(doc.getLength(), text+'\n', consoleTextAttributeSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	private void appendErrorToConsole(String text){
		try {
			doc.insertString(doc.getLength(), text+'\n', errorTextAttributeSet);
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
}


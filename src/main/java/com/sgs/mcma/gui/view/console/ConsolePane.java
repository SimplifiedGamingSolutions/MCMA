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
    private ControllableProcess p;
    
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
    	consoleTextAttributeSet = new SimpleAttributeSet();
    	StyleConstants.setForeground(consoleTextAttributeSet, Color.LIGHT_GRAY);
    	errorTextAttributeSet = new SimpleAttributeSet();
    	StyleConstants.setForeground(errorTextAttributeSet, Color.RED);
	}

	private void populateConsolePane() {
		setLayout (new BorderLayout ());
		JScrollPane pane = new JScrollPane (consoleTextPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(pane, BorderLayout.CENTER);
        ConsoleCommandTextField field = new ConsoleCommandTextField();
        add(field,BorderLayout.SOUTH);
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
    		        	else if(getText().equals("stop")){
    		        		stopServer();
    		        	}
    		        	else{
	    		        	p.sendCommand(getText());
    		        	}
    		        	clearCommand();
    				}
    				else{
    					consoleTextPane.setText("");
						appendToJTextPane("No running server", getErrorTextStyle());
    		        	clearCommand();
    				}
    			}
    		});
    	}
    	private void clearCommand(){
    		setText("");
    	}
    }
	public JTextPane getTextPane(){
		return consoleTextPane;
	}
	public SimpleAttributeSet getErrorTextStyle(){
		return errorTextAttributeSet;
	}
	public SimpleAttributeSet getConsoleTextStyle(){
		return consoleTextAttributeSet;
	}
	public void appendToJTextPane(String text, SimpleAttributeSet att){
		try {
			doc.insertString(doc.getLength(), text, att);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		ConsolePane test = new ConsolePane();
		new TestFrame(test);
    	test.startServer();
    }
	
	public void startServer(){
		if(p==null){
			p = new ControllableProcess("C:\\Users\\dtaylor\\Source\\Repos\\Java\\SimplifiedGamingSolutions\\MCMA\\Server\\","minecraft_server.1.8.4.jar", this);
		}
		p.start();
	}
	public void stopServer(){
		p.sendCommand("stop");
		p.stop();
		appendToJTextPane("Server Stopped\n", getErrorTextStyle());
	}
}


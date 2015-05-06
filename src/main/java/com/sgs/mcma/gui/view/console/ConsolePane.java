package com.sgs.mcma.gui.view.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.sgs.mcma.gui.view.TestFrame;

public class ConsolePane extends JPanel{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4437042456429430463L;
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
		JScrollPane pane = new JScrollPane (consoleTextPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(pane, BorderLayout.CENTER);
        ConsoleCommandTextField field = new ConsoleCommandTextField();
        add(field,BorderLayout.SOUTH);
	}
	
	private void clearConsole(){
		consoleTextPane.setText("");
	}
	class ConsoleCommandTextField extends JTextField {
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1578273190259755762L;
		public ConsoleCommandTextField(){
    		super();
    		this.addActionListener(new ActionListener() {
    			
    			public void actionPerformed(ActionEvent e) {
		        	if(getText().equals("clear")){
		        		clearConsole();
		        	}
		        	else if(p != null && p.isAlive()){
    		        	if(getText().equals("stop")){
    		        		stopServer();
    		        	}
    		        	else{
	    		        	p.sendCommand(getText());
    		        	}
    		        	clearCommand();
    				}
    				else{
    					clearConsole();
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
			File file = new File("Server");
			//JOptionPane.showMessageDialog(this, file.getAbsolutePath());
			p = new ControllableProcess(file.getAbsolutePath()+"\\","forge-1.8-11.14.1.1334-universal.jar", this);
		}
		p.start();
	}
	public void stopServer(){
		p.sendCommand("stop");
		p.stop();
		p=null;
		appendToJTextPane("Server Stopped\n", getErrorTextStyle());
	}
}


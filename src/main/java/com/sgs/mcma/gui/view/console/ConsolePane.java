package com.sgs.mcma.gui.view.console;

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sgs.mcma.gui.view.TestFrame;

public class ConsolePane extends JPanel{
	
    private StreamableTextArea textControl = new StreamableTextArea();
    private BufferedReader input;
    public BufferedWriter output;
    private BufferedReader error;
    private Process p;
    
    public ConsolePane(){
		populateConsolePane();
    }
    
    private void populateConsolePane() {setLayout (new BorderLayout ());
        add(createConsoleScrollPane(), BorderLayout.CENTER);
        add(createCommandTextField(),BorderLayout.SOUTH);
        System.setOut(new PrintStream(textControl.getOutputStream()));
	}
    
	private JScrollPane createConsoleScrollPane() {
		return new JScrollPane (textControl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	private JTextField createCommandTextField() {
        return new ConsoleTextField(this);
	}
	
	private void CreateProcess(String command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        p = pb.start();
        Runnable serverListener = new Runnable(){
			public void run() {
		        String line;
		        String errorMessage;
		        output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		        input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		        try {
					while ((line = input.readLine()) != null) {
					  System.out.println(line);
					}
					while ((errorMessage = error.readLine()) != null) {
						  System.out.println(errorMessage);
						}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        };
        serverListener.run();
	}
	
	class ConsoleTextField extends JTextField {
    	public ConsoleTextField(final ConsolePane console){
    		super();
    		this.addActionListener(new ActionListener() {
    			
    			public void actionPerformed(ActionEvent e) {
    		        try {
    		        	JTextField text = (JTextField) e.getSource();
    		        	console.output.write(text.getText()+'\n');
    		        	console.output.flush();
    		        	text.setText("");
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    			}
    		});
    	}
    }
	
	class StreamableTextArea extends JTextArea {
    	private StreamableTextArea instance = this;
    	
    	public StreamableTextArea() {
    		setEditable(false);
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
	
	public static void main(String[] args) throws Exception {
		ConsolePane test = new ConsolePane();
		new TestFrame(test);
    	test.CreateProcess("cmd.exe");
    }
}


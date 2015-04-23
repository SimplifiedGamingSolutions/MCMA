package com.sgs.mcma.gui.view.console;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Panel;
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

import com.sgs.mcma.gui.view.BaseFrame;

public class ConsolePane extends Panel {
	private JTextArea textControl = new JTextArea (25, 80);
	BufferedWriter output;
	BufferedReader input;
	BufferedReader error;
	Process p;
	public ConsolePane(){
		createConsolePane();
	}
	public void Start(){
		try {
			p = CreateProcess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    private JPanel createConsolePane() {
        JPanel console = new JPanel();
        console.setLayout (new BorderLayout ());
        console.add (createConsoleScrollPane(), BorderLayout.CENTER);
        console.add(createCommandTextField(),BorderLayout.SOUTH);
        //System.setOut(new PrintStream(new TextAreaOutputStreamTest(textControl)));
        return console;
	}
	private Component createConsoleScrollPane() {
		return new JScrollPane ( textControl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	private JTextField createCommandTextField() {
        JTextField textField = new JTextField("hello");
        /*textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(output != null){
			        try {
			        	JTextField text = (JTextField) e.getSource();
						output.write(text.getText()+'\n');
						output.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});*/
        return textField;
	}
	private Process CreateProcess() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe");
        final Process p = pb.start();
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
        return p;
	}

	public class TextAreaOutputStream extends OutputStream {
	    private JTextArea textControl;
	    public TextAreaOutputStream( JTextArea control ) {
	        textControl = control;
	    }
	    public void write( int b ) throws IOException {
	        textControl.append( String.valueOf( ( char )b ) );
	    }  
	}

	public static void main(String[] args) throws Exception {
		final ConsolePane console = new ConsolePane();
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				JFrame frame = new JFrame("cmd");
				frame.getContentPane().setLayout(new BorderLayout());
				frame.setSize(1024, 700);
				frame.setVisible(true);
				frame.getContentPane().add(console);
			}
		});
		//console.Start();
    }
}




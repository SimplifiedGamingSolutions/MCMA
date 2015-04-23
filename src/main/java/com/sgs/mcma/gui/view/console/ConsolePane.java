package com.sgs.mcma.gui.view.console;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sgs.mcma.gui.view.BaseFrame;

public class ConsolePane extends Panel {

    private BufferedWriter output;
    private JTextArea textControl;
	public ConsolePane() throws Exception{
		JTextArea textArea = new JTextArea (25, 80);
        textArea.setEditable (true);
        Container contentPane = this;
        contentPane.setLayout (new BorderLayout ());
        contentPane.add (
            new JScrollPane (
                textArea, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
            BorderLayout.CENTER);
        JTextField textField = new JTextField();
        textField.addActionListener(new ActionListener() {
			
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
        contentPane.add(textField,BorderLayout.SOUTH);
        System.setOut(new PrintStream(new TextAreaOutputStream(textArea)));
        ProcessBuilder pb = new ProcessBuilder("cmd.exe");
        final Process p = pb.start();
        Runnable serverListener = new Runnable(){
			public void run() {
		        String line;
		        String errorMessage;
		        output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
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
	class TextAreaOutputStream extends OutputStream {
	    public TextAreaOutputStream( JTextArea control ) {
	        textControl = control;
	    }
	    public void write( int b ) throws IOException {
	        textControl.append( String.valueOf( ( char )b ) );
	    }  
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				JFrame frame = new JFrame("Minecraft Mod Admin");
				frame.getContentPane().setLayout(new BorderLayout());
				try {
					ConsolePane cp = new ConsolePane();
					frame.getContentPane().add(cp, BorderLayout.CENTER);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.setVisible(true);
			}
		});
	}
}




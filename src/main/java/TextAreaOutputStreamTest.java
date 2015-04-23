

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

import com.sgs.mcma.gui.view.console.ConsoleTextField;
import com.sgs.mcma.gui.view.console.StreamableTextArea;

public class TextAreaOutputStreamTest {
    private StreamableTextArea textControl = new StreamableTextArea(50,25);
    private BufferedReader input;
    private BufferedWriter output;
    private BufferedReader error;
    private void CreateTestFrame(){
        JFrame frame = new JFrame ("cmd");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(createConsolePane(), BorderLayout.CENTER);
        frame.pack ();
        frame.setVisible (true);
    }
    private JPanel createConsolePane() {
        JPanel console = new JPanel();
        console.setLayout (new BorderLayout ());
        console.add (createConsoleScrollPane(), BorderLayout.CENTER);
        console.add(createCommandTextField(),BorderLayout.SOUTH);
        System.setOut(new PrintStream(textControl.getOutputStream()));
        return console;
	}
	private JScrollPane createConsoleScrollPane() {
		return 
	            new JScrollPane (
	                    textControl, 
	                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
	                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	private JTextField createCommandTextField() {
        return new ConsoleTextField(output);
	}
	private void CreateProcess() throws IOException {
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
	}
	public static void main(String[] args) throws Exception {
		TextAreaOutputStreamTest test = new TextAreaOutputStreamTest();
    	test.CreateTestFrame();
    	test.CreateProcess();
    }
}

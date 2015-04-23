

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TextAreaOutputStreamTest extends OutputStream {
    private JTextArea textControl;
    public TextAreaOutputStreamTest( JTextArea control ) {
        textControl = control;
    }
    public void write( int b ) throws IOException {
        textControl.append( String.valueOf( ( char )b ) );
    }  
    public static BufferedWriter output;
    public static void main(String[] args) throws Exception {
        JTextArea textArea = new JTextArea (25, 80);
        textArea.setEditable (true);
        JFrame frame = new JFrame ("cmd");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane ();
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
        frame.pack ();
        frame.setVisible (true);
        System.setOut(new PrintStream(new TextAreaOutputStreamTest(textArea)));
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
}

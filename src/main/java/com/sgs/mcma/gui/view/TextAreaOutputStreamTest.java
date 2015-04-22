package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

@SuppressWarnings("serial")
public class TextAreaOutputStreamTest extends JPanel {

   private JTextArea textArea = new JTextArea(15, 30);
   private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea, "");
   private TextAreaInputStream taInputStream = new TextAreaInputStream(textArea);

   public TextAreaOutputStreamTest() {
      setLayout(new BorderLayout());
      add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
      System.setOut(new PrintStream(taOutputStream));
      System.setIn(taInputStream);
      textArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"doNothing");
      textArea.addKeyListener(new KeyListener() {
		
		public void keyTyped(KeyEvent e) {
		}
		
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				try {
					JTextArea textarea = (JTextArea)e.getSource();
					Document document = textarea.getDocument();
					Element rootElem = document.getDefaultRootElement();
					int numLines = rootElem.getElementCount();
					Element lineElem = rootElem.getElement(numLines - 2);
					int lineStart = lineElem.getStartOffset();
					int lineEnd = lineElem.getEndOffset();
					String lineText = document.getText(lineStart, lineEnd - lineStart);
					lineText = lineText.trim();
					String out = "'"+lineText+"'";
					System.out.println(out);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		public void keyPressed(KeyEvent e) {
		}
	});
      System.out.println("Output working");
   }

   private static void createAndShowGui() {
      JFrame frame = new JFrame("Test");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(new TextAreaOutputStreamTest());
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}

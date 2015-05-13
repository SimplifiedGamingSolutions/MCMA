package com.sgs.mcma.gui.view;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

@SuppressWarnings("serial")
public class ServerLogTab extends JTabbedPane {
	public static ServerLogTab instance;
	private static RTextScrollPane allMessagesTextArea;
	private static RTextScrollPane errorMessagesTextArea;
	private static RTextScrollPane chatMessagesTextArea;
	public ServerLogTab() {
		instance = this;
		allMessagesTextArea = CreateSyntaxTextArea("all");
		errorMessagesTextArea = CreateSyntaxTextArea("error");
		chatMessagesTextArea = CreateSyntaxTextArea("chat");
		
		addTab("All Messages", allMessagesTextArea);
		addTab("Error Messages", errorMessagesTextArea);
		addTab("Chat Messages", chatMessagesTextArea);

	      this.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				if(instance.getSelectedIndex()==0)
					updateAllMessages();
				else if(instance.getSelectedIndex()==1)
					updateErrorMessages();
				else
					updateChatMessages();
			}
		});
	}
	
	public void updateAllMessages(){
		try {
			allMessagesTextArea.getTextArea().setText(new String(Files.readAllBytes(Paths.get("Server\\logs\\latest.log")), Charset.defaultCharset()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateErrorMessages(){
		try {
			errorMessagesTextArea.getTextArea().setText(new String(Files.readAllBytes(Paths.get("Server\\logs\\latest.log")), Charset.defaultCharset()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateChatMessages(){
		try {
			chatMessagesTextArea.getTextArea().setText(new String(Files.readAllBytes(Paths.get("Server\\logs\\latest.log")), Charset.defaultCharset()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private RTextScrollPane CreateSyntaxTextArea(String title) 
	{
	      final RSyntaxTextArea textArea;
	      textArea = new RSyntaxTextArea(20, 60);
	      textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
	      textArea.setCodeFoldingEnabled(true);
	      textArea.setAntiAliasingEnabled(true);
	      textArea.setEditable(false);
	      final RTextScrollPane sp = new RTextScrollPane(textArea);
	      sp.setName(title);
	      sp.setFoldIndicatorEnabled(true);
	      //cp.setPreferredSize(new Dimension(1024, 700));
	      return sp;
	}
}

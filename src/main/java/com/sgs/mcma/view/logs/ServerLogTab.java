package com.sgs.mcma.view.logs;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.sgs.mcma.controller.logs.LogTabController;

@SuppressWarnings("serial")
public class ServerLogTab extends JTabbedPane
{
	public static ServerLogTab instance;
	private static RTextScrollPane allMessagesTextArea;
	private static RTextScrollPane errorMessagesTextArea;
	private static RTextScrollPane chatMessagesTextArea;

	public ServerLogTab()
	{
		ServerLogTab.instance = this;
		ServerLogTab.allMessagesTextArea = CreateSyntaxTextArea("all");
		ServerLogTab.errorMessagesTextArea = CreateSyntaxTextArea("error");
		ServerLogTab.chatMessagesTextArea = CreateSyntaxTextArea("chat");

		this.addTab("All Messages", ServerLogTab.allMessagesTextArea);
		this.addTab("Error Messages", ServerLogTab.errorMessagesTextArea);
		this.addTab("Chat Messages", ServerLogTab.chatMessagesTextArea);

		addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				LogTabController.logTabChanged(ServerLogTab.instance);
			}
		});
	}

	public void updateAllMessages()
	{
		try
		{
			ServerLogTab.allMessagesTextArea.getTextArea().setText(new String(Files.readAllBytes(Paths.get("Server\\logs\\latest.log")), Charset.defaultCharset()).trim());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateErrorMessages()
	{
		try
		{
			List<String> AllMessages = Files.readAllLines(Paths.get("Server\\logs\\latest.log"));
			String errorMessages = "";
			for (String line : AllMessages)
			{
				if (line.contains("ERROR"))
				{
					errorMessages += line + '\n';
				}
			}
			ServerLogTab.chatMessagesTextArea.getTextArea().setText(errorMessages.trim());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateChatMessages()
	{
		try
		{
			List<String> AllMessages = Files.readAllLines(Paths.get("Server\\logs\\latest.log"));
			String chatMessages = "";
			for (String line : AllMessages)
			{
				if (line.contains("]: <"))
				{
					chatMessages += line + '\n';
				}
			}
			ServerLogTab.chatMessagesTextArea.getTextArea().setText(chatMessages.trim());
		} catch (IOException e)
		{
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
		// cp.setPreferredSize(new Dimension(1024, 700));
		return sp;
	}
}

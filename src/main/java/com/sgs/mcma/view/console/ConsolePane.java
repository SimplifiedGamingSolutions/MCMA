package com.sgs.mcma.view.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

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

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sgs.mcma.controller.summary.ConsolePaneController;
import com.sgs.mcma.utils.Utils;
import com.sgs.mcma.view.test.TestFrame;

@SuppressWarnings("serial")
public class ConsolePane extends JPanel
{

	private JTextPane consoleTextPane;
	private StyledDocument doc;
	private SimpleAttributeSet consoleTextAttributeSet;
	private SimpleAttributeSet errorTextAttributeSet;
	private ControllableProcess p;
	private ConsolePane instance;

	public ConsolePane()
	{
		instance = this;
		populateTextPane();
		populateConsolePane();
	}

	public ConsolePane Instance()
	{
		return instance;
	}

	private void populateTextPane()
	{
		consoleTextPane = new JTextPane();
		DefaultCaret caret = (DefaultCaret) consoleTextPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		consoleTextPane.setBackground(Color.BLACK);
		consoleTextPane.setEditable(false);
		doc = consoleTextPane.getStyledDocument();
		consoleTextAttributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(consoleTextAttributeSet, Color.LIGHT_GRAY);
		errorTextAttributeSet = new SimpleAttributeSet();
		StyleConstants.setForeground(errorTextAttributeSet, Color.RED);
	}

	public ConsoleCommandTextField field;

	private void populateConsolePane()
	{
		setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane(consoleTextPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(pane, BorderLayout.CENTER);
		field = new ConsoleCommandTextField();
		this.add(field, BorderLayout.SOUTH);
	}

	public void clearConsole()
	{
		consoleTextPane.setText("");
	}

	class ConsoleCommandTextField extends JTextField
	{

		public ConsoleCommandTextField()
		{
			addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent e)
				{
					ConsolePaneController.commandTextFieldActionTriggered(ConsoleCommandTextField.this.getText(), instance);
				}
			});
		}

		private void clearCommand()
		{
			setText("");
		}
	}

	public JTextPane getTextPane()
	{
		return consoleTextPane;
	}

	public SimpleAttributeSet getErrorTextStyle()
	{
		return errorTextAttributeSet;
	}

	public SimpleAttributeSet getConsoleTextStyle()
	{
		return consoleTextAttributeSet;
	}

	public void appendToJTextPane(String text, SimpleAttributeSet att)
	{
		try
		{
			doc.insertString(doc.getLength(), text, att);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception
	{
		ConsolePane test = new ConsolePane();
		new TestFrame(test);
		test.startServer();
	}

	public void startServer()
	{
		if (p == null)
		{
			File file = new File("Server");
			File forgeJar = new File("Server\\forge.jar");
			if(!forgeJar.exists())
			{
				downloadForge();
			}
			p = new ControllableProcess(file.getAbsolutePath() + "\\", "forge.jar", this);
		}
		p.start();
	}

	private void downloadForge() {
		try
		{
			Document doc = Jsoup.connect("http://files.minecraftforge.net/maven/net/minecraftforge/forge/")
					  .userAgent("Chrome/33.0.1750.152")
					  .get();
			Elements forgeInstaller = doc.select("a[href]").select("a[title$=Installer]"); // a with href
			ArrayList<String> installers = new ArrayList<String>();
			for(Element el : forgeInstaller){
				String line = el.toString();
				installers.add(el.toString().substring(line.indexOf("url=")+4, line.indexOf(".jar")+4));
			}
			String url = installers.get(1);
			String filename = url.substring(url.indexOf("forge-"), url.length());
			Utils.saveUrl("Server\\"+filename, installers.get(1));
			ProcessBuilder installer = new ProcessBuilder("java", "-jar", filename, "--installServer");
			installer.directory(new File("Server"));
			installer.inheritIO();
			Process p = installer.start();
			while(p.isAlive()){}
			
			Elements forgeUniversal = doc.select("a[href]").select("a[title$=Universal]"); // a with href
			ArrayList<String> universals = new ArrayList<String>();
			for(Element el : forgeUniversal){
				String line = el.toString();
				universals.add(el.toString().substring(line.indexOf("url=")+4, line.indexOf(".jar")+4));
			}
			url = universals.get(1);
			filename = "Server\\" + url.substring(url.indexOf("forge-"), url.length());
			Utils.saveUrl("Server\\forge.jar", universals.get(1));
			
			FileUtils.copyFileToDirectory(new File("Resources\\eula.txt"), new File("Server\\"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void stopServer()
	{
		p.sendCommand("stop");
		p.stop();
		p = null;
		appendToJTextPane("Server Stopped\n", getErrorTextStyle());
	}

	public void sendCommand(String command)
	{
		p.sendCommand(command);
	}

	public boolean isRunning()
	{
		if (p != null)
		{
			return p.isAlive();
		} else
		{
			return false;
		}
	}

	public void clearTextField()
	{
		field.clearCommand();
	}
}

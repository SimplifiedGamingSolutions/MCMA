package com.sgs.mcma.view.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.sgs.mcma.controller.settings.ServerSettingsController;

@SuppressWarnings("serial")
public class ServerSettingsTab extends JTabbedPane
{
	private static ServerSettingsTab instance;
	private static RSyntaxTextArea textArea;

	public ServerSettingsTab()
	{
		ServerSettingsTab.instance = this;
		setTabPlacement(SwingConstants.LEFT);
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		DirectoryTreeView dt = new DirectoryTreeView(new File("Server"));
		JScrollPane treeView = new JScrollPane(dt);
		splitPane.setLeftComponent(treeView);
		splitPane.setRightComponent(CreateSyntaxTextArea());
		splitPane.setPreferredSize(new Dimension(1024, 700)); // size needed in
		// order to set
		// divider
		// location
		splitPane.setDividerLocation(.5);

		tab1.add(splitPane, BorderLayout.CENTER);

		this.addTab("Server Config", tab1);
		this.addTab("Mod Config", new JPanel());
	}

	public static ServerSettingsTab Instance()
	{
		return ServerSettingsTab.instance;
	}

	private Component CreateSyntaxTextArea()
	{
		JPanel cp = new JPanel(new BorderLayout());
		ServerSettingsTab.textArea = new RSyntaxTextArea(20, 60);
		ServerSettingsTab.textArea
		.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		ServerSettingsTab.textArea.setCodeFoldingEnabled(true);
		ServerSettingsTab.textArea.setAntiAliasingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(ServerSettingsTab.textArea);
		sp.setFoldIndicatorEnabled(true);
		cp.add(sp, BorderLayout.CENTER);
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ServerSettingsController
				.saveButtonPressed(ServerSettingsTab.instance);
			}
		});
		cp.add(saveButton, BorderLayout.SOUTH);
		return cp;
	}

	public void writeTextAreaToFile()
	{
		try
		{
			String text = ServerSettingsTab.textArea.getText();
			File selectedFile = DirectoryTreeView.instance
					.getFileForSelectedNode();
			FileWriter fw = new FileWriter(selectedFile);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static RSyntaxTextArea getTextArea()
	{
		return ServerSettingsTab.textArea;
	}

	public void writeFileToTextArea(File file)
	{
		String text = "";
		try
		{
			text = new String(Files.readAllBytes(Paths.get(file.getPath())),
					Charset.defaultCharset());
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		ServerSettingsTab.textArea.setText(text);
	}
}

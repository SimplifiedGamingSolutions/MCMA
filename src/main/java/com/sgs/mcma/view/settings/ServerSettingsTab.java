package com.sgs.mcma.view.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import scala.swing.FileChooser;

import com.sgs.mcma.controller.settings.ServerSettingsController;
import com.sgs.mcma.view.BaseFrame;
import com.sgs.mcma.view.logs.ServerLogTab;

import de.muntjak.tinylookandfeel.Theme;
import de.muntjak.tinylookandfeel.TinyLookAndFeel;

@SuppressWarnings("serial")
public class ServerSettingsTab extends JTabbedPane
{
	private static ServerSettingsTab instance;
	private static RSyntaxTextArea textArea;

	public ServerSettingsTab()
	{
		ServerSettingsTab.instance = this;
		//this.setUI(new WindowsTabbedPaneUI());
		setTabPlacement(SwingConstants.LEFT);
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		File serverDir = new File("Server");
		if(!serverDir.exists())
		{
			serverDir.mkdirs();
		}
		DirectoryTreeView dt = new DirectoryTreeView(serverDir);
		JScrollPane treeView = new JScrollPane(dt);
		splitPane.setLeftComponent(treeView);
		splitPane.setRightComponent(CreateSyntaxTextArea());
		splitPane.setPreferredSize(new Dimension(1024, 700)); // size needed in
		// order to set
		// divider
		// location
		splitPane.setDividerLocation(.5);

		tab1.add(splitPane, BorderLayout.CENTER);

		this.addTab("Server", tab1);
		this.addTab("Mod", createModConfig());
	}

	private JPanel createModConfig()
	{
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
		
		Box inactiveMods = Box.createVerticalBox();
		inactiveMods.add(Box.createVerticalStrut(10));
		inactiveMods.add(new JLabel("Inactive Mods"));
		final JList<String> inactiveModsList = new JList<String>(new DefaultListModel<String>());
		final JList<String> activeModsList = new JList<String>(new DefaultListModel<String>());
		ServerSettingsController.populateInactiveModsList((DefaultListModel<String>)inactiveModsList.getModel());
		
		inactiveMods.add(new JScrollPane(inactiveModsList));
		inactiveMods.add(Box.createVerticalStrut(10));
		
		Box buttons = Box.createVerticalBox();
		buttons.add(Box.createVerticalGlue());
		JButton activateModButton = new JButton("   Activate -->  ");
		activateModButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ServerSettingsController.activateMod(inactiveModsList.getSelectedValue(), (DefaultListModel<String>)inactiveModsList.getModel(), (DefaultListModel<String>)activeModsList.getModel());
			}
		});
		buttons.add(activateModButton);
		buttons.add(Box.createVerticalStrut(20));
		
		JButton addExternalMod = new JButton(" Add External ");
		addExternalMod.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ServerSettingsController.addExternalMod((DefaultListModel<String>)inactiveModsList.getModel(), (DefaultListModel<String>)activeModsList.getModel());
			}
		});
		buttons.add(addExternalMod);
		buttons.add(Box.createVerticalStrut(20));

		Box activeMods = Box.createVerticalBox();
		activeMods.add(Box.createVerticalStrut(10));
		activeMods.add(new JLabel("Active Mods"));
		ServerSettingsController.populateActiveModsList((DefaultListModel<String>)activeModsList.getModel());
		activeMods.add(new JScrollPane(activeModsList));
		activeMods.add(Box.createVerticalStrut(10));
		
		JButton deactivateModButton = new JButton("<-- Deactivate");
		deactivateModButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ServerSettingsController.deactivateMod(activeModsList.getSelectedValue(), (DefaultListModel<String>)inactiveModsList.getModel(), (DefaultListModel<String>)activeModsList.getModel());
			}
		});
		buttons.add(deactivateModButton);
		buttons.add(Box.createVerticalGlue());
		

		jp.add(Box.createHorizontalStrut(20));
		jp.add(inactiveMods);
		jp.add(Box.createHorizontalGlue());
		jp.add(buttons);
		jp.add(Box.createHorizontalGlue());
		jp.add(activeMods);
		jp.add(Box.createHorizontalStrut(20));
		
		
		return jp;
	}

	public static ServerSettingsTab Instance()
	{
		return ServerSettingsTab.instance;
	}

	private Component CreateSyntaxTextArea()
	{
		JPanel cp = new JPanel(new BorderLayout());
		ServerSettingsTab.textArea = new RSyntaxTextArea(20, 60);
		ServerSettingsTab.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
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
				ServerSettingsController.saveButtonPressed(ServerSettingsTab.instance);
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
			File selectedFile = DirectoryTreeView.instance.getFileForSelectedNode();
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
			text = new String(Files.readAllBytes(Paths.get(file.getPath())), Charset.defaultCharset());
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		ServerSettingsTab.textArea.setText(text);
	}
}

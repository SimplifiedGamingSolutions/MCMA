package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

@SuppressWarnings("serial")
public class ServerSettingsTab extends JTabbedPane {
	private static RSyntaxTextArea textArea;
	
	public JTabbedPane createTab2() 
	{
		JTabbedPane config = new JTabbedPane();
		config.setTabPlacement(SwingConstants.LEFT);
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		ArrayList<String> exclusions = new ArrayList<String>();
		exclusions.add(".jar");
		exclusions.add(".log");
	    DirectoryTreeView dt = new DirectoryTreeView(new File("Server"), exclusions);
	    JScrollPane treeView = new JScrollPane(dt);
		splitPane.setLeftComponent(treeView);
		splitPane.setRightComponent(CreateSyntaxTextArea());
		splitPane.setPreferredSize(new Dimension(1024, 700)); //size needed in order to set divider location
		splitPane.setDividerLocation(.5);
		
		tab1.add(splitPane, BorderLayout.CENTER);

		config.addTab("Server Config", tab1);
		config.addTab("Mod Config", new JPanel());
		return config;
	}
	
	private Component CreateSyntaxTextArea() 
	{
	      JPanel cp = new JPanel(new BorderLayout());
	      textArea = new RSyntaxTextArea(20, 60);
	      textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
	      textArea.setCodeFoldingEnabled(true);
	      textArea.setAntiAliasingEnabled(true);
	      RTextScrollPane sp = new RTextScrollPane(textArea);
	      sp.setFoldIndicatorEnabled(true);
	      cp.add(sp, BorderLayout.CENTER);
	      JButton saveButton = new JButton("Save");
	      saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String text = textArea.getText();
					File selectedFile = DirectoryTreeView.instance.getFileForSelectedNode();
					FileWriter fw = new FileWriter(selectedFile);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(text);
					bw.close();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
	      cp.add(saveButton, BorderLayout.SOUTH);
	      return cp;
	}
	
	public static RSyntaxTextArea getTextArea(){
		return textArea;
	}
}

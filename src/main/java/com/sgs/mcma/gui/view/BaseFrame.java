package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class BaseFrame extends JFrame {
	
	public BaseFrame(String title){
		Initialize(title);
	}

	private void Initialize(String title) {
		this.setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(1024,700));
		populateFrame();
	}

	private void populateFrame() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Tab 1", new JPanel());
		tabs.addTab("Tab 2", new JPanel());
		tabs.addTab("Tab 3", new JPanel());
		
		mainPanel.add(tabs, BorderLayout.CENTER);
		
		this.add(mainPanel);
	}

}

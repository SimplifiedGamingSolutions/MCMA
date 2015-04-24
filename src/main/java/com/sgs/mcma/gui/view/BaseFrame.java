package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javafx.scene.control.Alert;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import com.sgs.mcma.gui.view.console.ConsolePane;

public class BaseFrame extends JFrame {
	private static BaseFrame instance;
	private static ConsolePane console;
	public static BaseFrame Instance(){
		return instance;
	}

	protected Process serverProcess;
	public BaseFrame(String title, int width, int height){
		super();
		instance = this;
		Initialize(title, width, height);
	}

	private void Initialize(String title, int width, int height) {
		this.setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(width,height));
		populateContentPane();
	}

	private void populateContentPane() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createTabs(), BorderLayout.CENTER);
	}
	
	private JTabbedPane createTabs() {
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Tab 1", createTab1());
		tabs.addTab("Tab 2", new JPanel());
		tabs.addTab("Tab 3", new JPanel());
		return tabs;
	}

	private JPanel createTab1() {
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		console = new ConsolePane();
		tab1.add(console, BorderLayout.CENTER);
		tab1.add(createButtonPanel(), BorderLayout.SOUTH);
		return tab1;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(createButtonBox());
		return buttonPanel;
	}

	private Box createButtonBox() {
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(createStartServerButton());
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(createStopServerButton());
		buttonBox.add(Box.createHorizontalGlue());
		return buttonBox;
	}

	private JButton createStartServerButton(){
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer();
			}

			private void startServer() {
				Process process = BaseFrame.Instance().console.CreateProcess("cmd.exe");
				if(process.isAlive()){
					JOptionPane.showMessageDialog(BaseFrame.Instance(), "server started");
				}
				else{
					JOptionPane.showMessageDialog(BaseFrame.Instance(), "server failed to start");
				}
			}
		});
		return startButton;
	}
	private JButton createStopServerButton(){
		JButton stopButton = new JButton("STOP");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}

			private void stopServer() {
				if(BaseFrame.Instance().console.stopProcess()){
					JOptionPane.showMessageDialog(BaseFrame.Instance(), "server stopped");
				}
				else{
					JOptionPane.showMessageDialog(BaseFrame.Instance(), "server wasn't started");
				}
			}
		});
		return stopButton;
	}
}

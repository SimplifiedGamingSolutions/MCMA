package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
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

public class BaseFrame extends JFrame {
	private static BaseFrame instance;
	public static BaseFrame Instance(){
		return instance;
	}

	protected Process serverProcess;
	public BaseFrame(String title){
		instance = this;
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
		
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer();
			}

			private void startServer() {
				try {
					BaseFrame.Instance().serverProcess = Runtime.getRuntime().exec("java -jar -Xmx1024M -Xms1024M minecraft_server.1.8.4.jar nogui");
					Process process = BaseFrame.Instance().serverProcess;
					if(process.isAlive()){
						JOptionPane.showMessageDialog(BaseFrame.Instance(), "server started");
					}
					else{
						JOptionPane.showMessageDialog(BaseFrame.Instance(), "server failed to start");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton stopButton = new JButton("STOP");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}

			private void stopServer() {
				Process process = BaseFrame.Instance().serverProcess;
				if(process.isAlive()){
					process.destroy();
					JOptionPane.showMessageDialog(BaseFrame.Instance(), "server stopped");
				}
				else{
					JOptionPane.showMessageDialog(BaseFrame.Instance(), "server wasn't started");
				}
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(startButton);
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(stopButton);
		buttonBox.add(Box.createHorizontalGlue());
		
		buttonPanel.add(buttonBox);
		
		tab1.add(buttonPanel);
		
		tabs.addTab("Tab 1", tab1);
		tabs.addTab("Tab 2", new JPanel());
		tabs.addTab("Tab 3", new JPanel());
		
		mainPanel.add(tabs, BorderLayout.CENTER);
		
		this.add(mainPanel);
	}

}

package com.sgs.mcma.gui.view;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.sgs.mcma.gui.view.console.ConsolePane;

@SuppressWarnings("serial")
public class Tab1 extends JPanel {
	
	ConsolePane console;
	private static Tab1 instance;
	public static DefaultListModel<String> playerListModel = new DefaultListModel<String>();

	public Tab1(ConsolePane c){
		console = c;
		instance = this;
		
	}
	public static Tab1 Instance(){
		return instance;
	}
	
	public JPanel createTab1() 
	{
		JPanel tab1 = new JPanel();
		tab1.setLayout(new BorderLayout());
		tab1.add(new PlayerListPanel(console,playerListModel), BorderLayout.WEST);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(console, BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		tab1.add(panel, BorderLayout.CENTER);
		return tab1;
	}
	
	private JPanel createButtonPanel() 
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(createButtonBox());
		return buttonPanel;
	}
	
	private Box createButtonBox() 
	{
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(createStartServerButton());
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(createStopServerButton());
		buttonBox.add(Box.createHorizontalGlue());
		return buttonBox;
	}
	
	private JButton createStartServerButton()
	{
		ImageIcon startBtn = new ImageIcon("Resources\\StartBtn.png");
		JButton startButton = new JButton(startBtn);
		startButton.setContentAreaFilled(false);
		startButton.setMargin(new Insets(-2, -2, -2, -2));
		startButton.setFocusPainted(false);
		startButton.setOpaque(false);
		startButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				startServer();
			}

			private void startServer() 
			{
				console.startServer();
			}
		});
		return startButton;
	}
	
	private JButton createStopServerButton()
	{
		ImageIcon stopBtn = new ImageIcon("Resources\\StopBtn.png");
		JButton stopButton = new JButton(stopBtn);
		stopButton.setContentAreaFilled(false);
		stopButton.setMargin(new Insets(-2, -2, -2, -2));
		stopButton.setFocusPainted(false);
		stopButton.setOpaque(false);
		stopButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				stopServer();
			}

			private void stopServer() 
			{
				console.stopServer();
				playerListModel.clear();
			}
		});
		return stopButton;
	}

}
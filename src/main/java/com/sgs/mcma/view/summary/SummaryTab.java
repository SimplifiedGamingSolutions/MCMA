package com.sgs.mcma.view.summary;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.scene.control.Label;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sgs.mcma.controller.summary.SummaryTabController;
import com.sgs.mcma.view.console.ConsolePane;

@SuppressWarnings("serial")
public class SummaryTab extends JPanel
{

	ConsolePane console;
	private static SummaryTab instance;
	public static DefaultListModel<String> playerListModel = new DefaultListModel<String>();

	public SummaryTab(ConsolePane c)
	{
		console = c;
		SummaryTab.instance = this;
		setLayout(new BorderLayout());
		SummaryTab.playerListModel.addElement("TestPlayer");
		this.add(new PlayerListPanel(console, SummaryTab.playerListModel), BorderLayout.WEST);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(console, BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		this.add(panel, BorderLayout.CENTER);
	}
	
	private JLabel addIPAddress()
	{
		JLabel ipAddress = new JLabel(SummaryTabController.displayIP());
		ipAddress.setFont(this.getFont().deriveFont(24.0f));
		return ipAddress;
	}

	public static SummaryTab Instance()
	{
		return SummaryTab.instance;
	}

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(createButtonBox());
		buttonPanel.add(addIPAddress());
		return buttonPanel;
	}

	private Box createButtonBox()
	{
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(createStartServerButton());
		buttonBox.add(createStopServerButton());
		buttonBox.add(Box.createHorizontalGlue());
		return buttonBox;
	}
	private JButton startButton;
	private JButton stopButton;
	private JButton createStartServerButton()
	{
		ImageIcon startBtn = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/StartBtn.png"));
		startButton = new JButton(startBtn);
		startButton.setContentAreaFilled(false);
		startButton.setMargin(new Insets(-2, -2, -2, -2));
		startButton.setFocusPainted(false);
		startButton.setOpaque(false);
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(SummaryTabController.startButtonPressed(SummaryTab.instance)){
					startButton.setVisible(false);
					stopButton.setVisible(true);
				}
			}
		});
		return startButton;
	}

	private JButton createStopServerButton()
	{
		ImageIcon stopBtn = new ImageIcon(ClassLoader.getSystemResource("Resources/Images/StopBtn.png"));
		stopButton = new JButton(stopBtn);
		stopButton.setContentAreaFilled(false);
		stopButton.setMargin(new Insets(-2, -2, -2, -2));
		stopButton.setFocusPainted(false);
		stopButton.setOpaque(false);
		stopButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(SummaryTabController.stopButtonPressed(SummaryTab.instance)){
					stopButton.setVisible(false);
					startButton.setVisible(true);
				}
			}
		});
		stopButton.setVisible(false);
		return stopButton;
	}

	public void clearPlayerList()
	{
		SummaryTab.playerListModel.clear();
	}

	public boolean startServer()
	{
		if (!console.isRunning())
		{
			console.startServer();
			return true;
		}
		return false;
	}

	public boolean stopServer()
	{
		if (console.isRunning())
		{
			console.stopServer();
			return true;
		}
		return false;
	}

}

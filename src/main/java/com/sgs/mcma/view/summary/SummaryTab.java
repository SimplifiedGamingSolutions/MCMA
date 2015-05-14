package com.sgs.mcma.view.summary;

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
		this.add(new PlayerListPanel(console, SummaryTab.playerListModel),
				BorderLayout.WEST);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(console, BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.SOUTH);
		this.add(panel, BorderLayout.CENTER);
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
				SummaryTabController.startButtonPressed(SummaryTab.instance);
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
				SummaryTabController.stopButtonPressed(SummaryTab.instance);
			}
		});
		return stopButton;
	}

	public void clearPlayerList()
	{
		SummaryTab.playerListModel.clear();
	}

	public void startServer()
	{
		if (!console.isRunning())
		{
			console.startServer();
		}
	}

	public void stopServer()
	{
		if (console.isRunning())
		{
			console.stopServer();
		}
	}

}

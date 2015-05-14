package com.sgs.mcma.controller.summary;

import com.sgs.mcma.view.console.ConsolePane;

public class ConsolePaneController
{

	public static void commandTextFieldActionTriggered(String text,
			ConsolePane console)
	{
		if (text.equals("clear"))
		{
			console.clearConsole();
		} else if (console.isRunning())
		{
			if (text.equals("stop"))
			{
				console.stopServer();
			} else
			{
				console.sendCommand(text);
			}
			console.clearTextField();
		} else
		{
			console.clearConsole();
			console.appendToJTextPane("No running server",
					console.getErrorTextStyle());
			console.clearTextField();
		}
	}

}

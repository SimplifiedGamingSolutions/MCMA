package com.sgs.mcma.view.console;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.swing.text.SimpleAttributeSet;

public class ControllableProcess
{
	private ProcessBuilder pb;
	private Process p;
	private JTextPaneInputStreamPrinter inputStreamPrinter;
	private Thread inputListener;
	private JTextPaneInputStreamPrinter errorStreamPrinter;
	private Thread errorListener;
	private BufferedWriter output;
	private ConsolePane console;

	public ControllableProcess(String jarPath, String jarName,
			ConsolePane console)
	{
		this.console = console;
		pb = new ProcessBuilder("java", "-jar", jarPath + jarName, "nogui");
		pb.directory(new File(jarPath));
	}

	public boolean start()
	{
		try
		{
			if (p == null)
			{
				p = pb.start();
				initializeIO();
				return true;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	private void initializeIO()
	{
		output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		inputStreamPrinter = new JTextPaneInputStreamPrinter(
				p.getInputStream(), false);
		inputListener = new Thread(inputStreamPrinter);
		errorStreamPrinter = new JTextPaneInputStreamPrinter(
				p.getErrorStream(), true);
		errorListener = new Thread(errorStreamPrinter);
		inputListener.start();
		errorListener.start();
	}

	public boolean stop()
	{
		try
		{
			p.waitFor();
			p.destroy();
			p = null;
			inputStreamPrinter.pause();
			inputStreamPrinter = null;
			errorStreamPrinter.pause();
			errorStreamPrinter = null;
			inputListener.join();
			errorListener.join();
			inputListener = null;
			errorListener = null;
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void sendCommand(String text)
	{
		try
		{
			output.write(text + '\n');
			output.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isAlive()
	{
		return p.isAlive();
	}

	private class JTextPaneInputStreamPrinter implements Runnable
	{
		private boolean running = true;
		private boolean isErrorStream;
		private InputStream stream;
		byte[] inBuffer = new byte[1024];

		public JTextPaneInputStreamPrinter(InputStream stream,
				boolean isErrorStream)
		{
			this.stream = stream;
			this.isErrorStream = isErrorStream;
		}

		public void run()
		{
			if (isErrorStream)
			{
				runStreamPrinter(console.getErrorTextStyle());
			} else
			{
				runStreamPrinter(console.getConsoleTextStyle());
			}
		}

		public void runStreamPrinter(SimpleAttributeSet textStyle)
		{
			while (running)
			{
				try
				{
					int length = stream.read(inBuffer);
					if (length > -1)
					{
						console.appendToJTextPane(new String(inBuffer, 0,
								length), textStyle);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		public void pause()
		{
			running = false;
		}
	}
}
